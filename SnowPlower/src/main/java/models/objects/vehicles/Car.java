package main.java.models.objects.vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.models.interfaces.ICleaning;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
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
