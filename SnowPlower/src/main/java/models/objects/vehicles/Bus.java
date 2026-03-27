package main.java.models.objects.vehicles;
import java.util.List;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;

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
    public void Move() {
        Console.print("-> Bus.Move()");
        Console.print("<- Bus.Move()");
    }
    public void Stop() {
        Console.print("-> Bus.Stop()");
        Console.print("<- Bus.Stop()");
    }
    public void Slipping() {
        Console.print("-> Bus.Slipping()");
        Console.print("<- Bus.Slipping()");
    }
    public void SetRoute(Intersection start, Intersection end) {
        Console.print("-> Bus.SetRoute(Intersection start, Intersection end)");
        Console.print("<- Bus.SetRoute(Intersection start, Intersection end)");
    }
}