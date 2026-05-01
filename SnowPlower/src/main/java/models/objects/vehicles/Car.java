package main.java.models.objects.vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.models.interfaces.ICleaning;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.FileHandler;
import main.java.models.objects.road.Intersection;

/**
 * A város normál forgalmát adó, önálló célokkal rendelkező jármű.
 */
public class Car extends VehicleBase
{

    public Car(double bs){
        super(bs);
        Console.print("\t!<<create>>Car");
    }

    @Override
    public void Move()
    {
         Console.print("-> Car.Move()");
         Console.print("<- Car.Move(): void");
    }

    @Override
    public void Stop()
    {
        Console.print("-> Car.Stop()");
        Console.print("<- Car.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        Console.print("-> Car.Slipping()");
        lane.changeState("blocked");
        Console.print("<- Car.Slipping(): void");
    }

    @Override
    public void SetRoute(List<Intersection> intersections)
    {
        Console.print("-> Car.SetRoute(intersections)");
        // TDA: a logika a VehicleBase.SetRoute-ban van — a Map-nek mondjuk meg,
        // hogy számolja ki az útvonalat, nem mi kérjük el az adatokat tőle.
        super.SetRoute(intersections);
        Console.print("<- Car.SetRoute(intersections)");
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("V");
        res.append("\nid=" + id);
        res.append("\ntype=Car");
        res.append("\ncurrentPosition=" + currentPosition);
        res.append("\nlane=" + lane.toList());
        res.append("\nbaseSpeed=" + baseSpeed);
        res.append("\nroute=");
        for (ILane lane : route) {
            res.append(lane.toList())
               .append(";");
        }
        return res.toString();
    }
    //Fileból betöltés, szinkronizáció
    private Integer pendingLane;
    private List<Integer> pendingRoute = new ArrayList<>();
    @Override
    protected void applyData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
           switch (entry.getKey()) {
            case "id" -> id = Integer.parseInt(entry.getValue());
            case "currentPosition" -> currentPosition = Integer.parseInt(entry.getValue());
            case "lane" -> pendingLane = Integer.parseInt(entry.getValue());
            case "baseSpeed" -> baseSpeed = Integer.parseInt(entry.getValue());
            case "route" -> pendingRoute = FileHandler.parseList(entry.getValue());
            default -> {break;}
           } 
        }
    }

    @Override
    public void resolve(Map<Integer, ILane> lanes, Map<Integer, ICleaning> heads){
        if (pendingLane != null) lane = lanes.get(pendingLane);
        route = pendingRoute.stream()
            .map(lanes::get)
            .collect(Collectors.toCollection(ArrayList::new));
        pendingLane = null;
        pendingRoute.clear();
    }
}
