package main.java.models.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;
import main.java.models.objects.vehicles.VehicleBase;

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
     * @param loc mentés helye
     * @param player játékos állapota
     * @param map pálya állapota
     * @return művelet sikeressége
     */
    public boolean saveState(String loc, Player player, main.java.models.objects.Map map) {
        Console.print("\t->FileHandler.saveState(" + loc +")");
        Console.print("\t<-FileHandler.saveState(" + loc +"): true");
        return true;
    }

    /**
     * Betölti a játék állapotát.
     * @param loc mentés helye
     * @param player betöltendő játékos objektum
     * @param map betöltendő pálya objektum
     * @return művelet sikeressége
     */
    public boolean loadState(String loc, Player player, main.java.models.objects.Map map) {
        //TODO finish function implementations
        Console.print("\t->FileHandler.loadState(" + loc +")");
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
                    }
                    case "V" -> {
                        IVehicle v = VehicleBase.create(sc);
                    }
                    case "H" -> {}
                    case "R" -> {}
                        
                
                    default -> {break;}
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        Console.print("\t<-FileHandler.loadState(" + loc +"): true");
        return true;
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
                     .toList();
    }
}
