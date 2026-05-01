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

    protected VehicleBase(double bs){
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

    @Override
    public void SetRoute(List<Intersection> intersections)
    {
        Console.print("-> VehicleBase.SetRoute(Intersection start, Intersection end)");
        Console.print("<- VehicleBase.SetRoute(Intersection start, Intersection end): void");
    }

    public void setLane(ILane l){
        lane = l;
    }
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
           .append(lane.toList()).append(", ")
           .append("\n\tRoute: ");
        for (ILane node : route) {
            res.append(node.toList())
               .append(", ");
        }
        return res.toString();
    }
    //Fileból betöltés, szinkronizálás
    protected Integer pendingLane;
    protected List<Integer> pendingRoute = new ArrayList<>();
    protected void applyData(Map<String, String> data){
        for (Entry<String, String> entry : data.entrySet()) {
            switch (entry.getKey()) {
                case "id" -> id = Integer.parseInt(entry.getValue());
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
