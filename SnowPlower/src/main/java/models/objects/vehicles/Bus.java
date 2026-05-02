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
import main.java.models.objects.road.Road;

/**
 * A közúti forgalomban részt vevő tömegközlekedési jármű, amely meghatározott útvonalon halad végig.
 */
public class Bus extends VehicleBase {
    private String LineName;
    private List<Road> line;

    /**
     * A Bus osztály konstruktora.
     * @param lineName A járat neve.
     * @param route Az útvonalat alkotó utak listája.
     */
    public Bus(double bs, String lineName, List<Road> route) {
        super(bs);
        Console.print("\t!<<create>>Bus");
        this.LineName = lineName;
        this.line = route;
    }
    @Override 
    public void Move() {
        Console.print("\t\t-> Bus.Move()");
        Console.print("\t\t<- Bus.Move()");
        Stop();
    }
    @Override
    public void Stop() {
        Console.print("\t\t\t-> Bus.Stop()");
        Console.print("\t\t\t<- Bus.Stop()");
    }
    @Override
    public void Slipping() {
        Console.print("\t\t-> Bus.Slipping()");
        Console.print("\t\t<- Bus.Slipping()");
    }
    @Override
    public void SetRoute(List<Intersection> intersections) {
        Console.print("\t-> Bus.SetRoute(intersections)");
        super.SetRoute(intersections);
        Console.print("\t<- Bus.SetRoute(intersections)");
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("V");
        res.append("\nid=" + id);
        res.append("\ntype=Bus");
        res.append("\ncurrentPosition=" + currentPosition);
        res.append("\nlane=" + (lane != null ? lane.toList() : ""));
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