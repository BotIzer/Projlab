package main.java.models.objects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.LaneBase;
import main.java.models.objects.road.Road;
import main.java.models.objects.vehicles.VehicleBase;
import main.java.models.objects.vehicles.heads.AttachmentBase;

import java.util.Map;
/**
 * A játékállapot lemezre mentéséért és betöltéséért felelős segédosztály.
 */
public class FileHandler {

    public FileHandler(){
        Console.print("\t!<<create>>FileHandler");
    }

    /**
     * Elmenti a játék állapotát.
     * @param loc mentés helye, ha létezik, felülírja.
     * @param player játékos állapota
     * @param map pálya állapota
     * @return művelet sikeressége
     */
    public boolean saveState(String loc, Player player, main.java.models.objects.Map map) {
        Console.print("\t->FileHandler.saveState(" + loc +")");
        boolean res = true;
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(loc)))) {
            pw.println(format(player, map));
        } catch (Exception e) {
            res = false;
            Console.print(e.getMessage());
        }
        Console.print("\t<-FileHandler.saveState(" + loc +"): " + res);
        return res;
    }

    /**
     * Betölti a játék állapotát.
     * @param loc mentés helye
     * @param player betöltendő játékos objektum
     * @param map betöltendő pálya objektum
     * @return művelet sikeressége
     */
    public boolean loadState(String loc, Player player, main.java.models.objects.Map map) {
        Console.print("\t->FileHandler.loadState(" + loc +")");
        player.clear();
        map.clear();
        boolean res = true;
        Map<Integer, IVehicle> vehicles = new HashMap<>();
        Map<Integer, Intersection> intersections = new HashMap<>();
        Map<Integer, Road> roads = new HashMap<>();
        Map<Integer, ILane> lanes = new HashMap<>();
        Map<Integer, ICleaning> heads = new HashMap<>();
        try (Scanner sc = new Scanner(new File(loc))){
            while (sc.hasNextLine()) {
                String header = sc.nextLine().trim();
                switch (header) {
                    case "P" -> player.load(sc);
                    case "I" -> {
                        Intersection i = new Intersection(sc);
                        intersections.put(Integer.parseInt(i.toList()), i);
                    }
                    case "L" -> {
                        ILane l = LaneBase.create(sc);
                        lanes.put(Integer.parseInt(l.toList()), l);
                    }
                    case "V" -> {
                        IVehicle v = VehicleBase.create(sc);
                        vehicles.put(Integer.parseInt(v.toList()), v);
                    }
                    case "H" -> {
                        ICleaning h = AttachmentBase.create(sc);
                        heads.put(Integer.parseInt(h.toList()), h);
                    }
                    case "R" -> {
                        Road r = Road.create(sc);
                        roads.put(Integer.parseInt(r.toList()), r);
                    }
                        
                
                    default -> {break;}
                }
            }
            player.resolve(vehicles, heads);
            vehicles.values().forEach(v -> {((VehicleBase)v).resolve(lanes, heads); 
                                                          map.addVehicle(v);});
            intersections.values().forEach(i -> {i.resolve(roads); 
                                                 map.addIntersections(i);});
            roads.values().forEach(r -> {r.resolve(lanes); 
                                         map.addRoad(r);});
            lanes.values().forEach(l -> ((LaneBase)l).resolve(intersections, vehicles));
            map.repairConnections();

        } catch (Exception e) {
            Console.print("Error reading file:\n" + e.getMessage());
            res = false;
        }

        Console.print("\t<-FileHandler.loadState(" + loc +"): " + res);
        return res;
    }
    /**
     * Létrehozza a mentési formátumnak megfelelő szöveget
     * @param p a játékos állapota
     * @param map a játék állapota
     * @return megformázott szöveg
     */
    public String format(Player p, main.java.models.objects.Map map){
        StringBuilder res = new StringBuilder();
        res.append(p.toString());
        res.append(map.toString());

        return res.toString();
    }
    /**
     * Segédfüggvény az objektumok szinkronizációjához,
     * Szöveges listát számok listájává konvertál
     * @param value Szöveges lista, számok pontosvesszővel elkülönítve
     * @return Számok listája
     */
    public static List<Integer> parseList(String value) {
        if (value.isEmpty()) return new ArrayList<>();
        return Arrays.stream(value.split(";"))
                     .filter(s -> !s.isBlank())
                     .map(Integer::parseInt)
                     .collect(Collectors.toCollection(ArrayList::new));
    }
}
