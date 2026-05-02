package main.java.models.objects.vehicles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import main.java.models.interfaces.*;
import main.java.models.objects.Console;
import main.java.models.objects.FileHandler;
import main.java.models.objects.road.Intersection;

/**
 * Minden jármű absztrakt alaposztálya, amely definiálja az alapvető mozgási képességeket és a környezettel való kapcsolatot.
 */
public abstract class VehicleBase implements IVehicle {
    protected int id;
    protected double currentPosition;
    protected ILane lane;
    protected double baseSpeed;
    protected ArrayList<ILane> route;
    // Referencia a játéktérképre, az útvonaltervezéshez
    protected main.java.models.objects.Map gameMap;

    protected static int idCtr = 0;
    
    protected static void syncId(int lastId) {
        if (lastId >= idCtr) {
            idCtr = lastId + 1;
        }
    }
    
    public static void reset() {
        idCtr = 0;
    }

    protected VehicleBase(double bs){
        id = idCtr++;
        currentPosition = 0.0;
        baseSpeed = bs;
    }

    @Override
    public void Move()
    {
        Console.print("-> VehicleBase.Move()");
        Console.print("<- VehicleBase.Move(): void");
    }

    @Override
    public void Stop()
    {
        Console.print("-> VehicleBase.Stop()");
        Console.print("<- VehicleBase.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        Console.print("-> VehicleBase.Slipping()");
        Console.print("<- VehicleBase.Slipping(): void");
    }

    /**
     * TDA: Megmondja a Map-nek, hogy számolja ki az útvonalat.
     *   1 elem  → jelenlegi sáv kezdőpontjától az adott kereszteződésig
     *   2 elem  → a két végpont közti legrövidebb út
     *   2+ elem → egymást követő waypoint-ok összefűzése
     */
    @Override
    public void SetRoute(List<Intersection> intersections)
    {
        Console.print("-> VehicleBase.SetRoute(intersections)");
        if (gameMap == null || intersections == null || intersections.isEmpty()) {
            Console.print("<- VehicleBase.SetRoute: gameMap vagy útvonal hiányzik");
            return;
        }

        List<ILane> result;

        if (intersections.size() == 1) {
            Intersection startNode = (lane != null) ? lane.getStart() : null;
            result = (startNode != null)
                ? gameMap.findRoute(startNode, intersections.get(0))
                : null;
        } else if (intersections.size() == 2) {
            result = gameMap.findRoute(intersections.get(0), intersections.get(1));
        } else {
            result = new ArrayList<>();
            for (int i = 0; i < intersections.size() - 1; i++) {
                List<ILane> segment = gameMap.findRoute(
                    intersections.get(i), intersections.get(i + 1));
                if (segment == null) { result = null; break; }
                result.addAll(segment);
            }
        }

        if (result != null) {
            route = new ArrayList<>(result);
            Console.print("<- VehicleBase.SetRoute: " + route.size() + " sáv beállítva");
        } else {
            Console.print("<- VehicleBase.SetRoute: nem található útvonal");
        }
    }

    public void setLane(ILane l){
        lane = l;
    }

    public void setMap(main.java.models.objects.Map m){
        gameMap = m;
    }

    /** Visszaadja az aktuális útvonalat. Főleg tesztelési célra. */
    public List<ILane> getRoute() { return route; }
    @Override
    public String toList(){
        return Integer.toString(id);
    }
    @Override
    public String printLong() {
        StringBuilder res = new StringBuilder(this.getClass().getSimpleName());

        res.append(id)
           .append(": ")
           .append(id).append(", ")
           .append(lane == null? "" :lane.toList()).append(", ")
           .append("\n\tRoute: ");
        if (route != null) {
            for (ILane node : route) {
                res.append(node.toList()).append(", ");
            }
        }
        return res.toString();
    }
    //Fileból betöltés, szinkronizálás
    protected Integer pendingLane;
    protected List<Integer> pendingRoute = new ArrayList<>();
    protected void applyData(Map<String, String> data){
        for (Entry<String, String> entry : data.entrySet()) {
            switch (entry.getKey()) {
                case "id" -> {
                    id = Integer.parseInt(entry.getValue());
                    syncId(id);
                }
                case "currentPosition" -> currentPosition = Double.parseDouble(entry.getValue());
                case "lane" -> pendingLane = Integer.parseInt(entry.getValue());
                case "baseSpeed" -> baseSpeed = Double.parseDouble(entry.getValue());
                case "route" -> pendingRoute = FileHandler.parseList(entry.getValue());
                default -> {break;}
            }
        }
    }
    public abstract void resolve(Map<Integer, ILane> lanes, Map<Integer, ICleaning> headsTmp);
    public static IVehicle create(Scanner sc){
        Map<String, String> data = new HashMap<>();
        
        while (sc.hasNext(".*=.*")) {
            String[] parts = sc.nextLine().split("=", 2);
            data.put(parts[0].trim(), parts.length > 1 ? parts[1].trim() : "");
        }

        String type = data.getOrDefault("type", "");

        IVehicle v = switch (type) {
            case "SnowPlower" -> new SnowPlower(30);
            case "Bus" -> new Bus(30, "", new ArrayList<>());
            case "Car" -> new Car(30);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };

        ((VehicleBase)v).applyData(data); 

        return v;
    }
}
