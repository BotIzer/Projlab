package main.java.models.objects.vehicles;
import java.util.List;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
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
    
    public void Move() {
        Console.print("\t\t-> Bus.Move()");
        Console.print("\t\t<- Bus.Move()");
        Stop();
    }

    public void Stop() {
        Console.print("\t\t\t-> Bus.Stop()");
        Console.print("\t\t\t<- Bus.Stop()");
    }

    public void Slipping() {
        Console.print("\t\t-> Bus.Slipping()");
        Console.print("\t\t<- Bus.Slipping()");
    }

    public void SetRoute(List<Intersection> intersections) {
        Console.print("\t-> Bus.SetRoute(Intersection start, Intersection end)");
        Move();
        Console.print("\t<- Bus.SetRoute(Intersection start, Intersection end)");
    }
    @Override
    public String toString() {
        String res = "V";
        res += "\nid=" + id;
        res += "\ntype=Bus";
        res += "\ncurrentPosition=" + currentPosition;
        res += "\nlane=" + lane.GetId();
        res += "\nbaseSpeed=" + baseSpeed;
        res += "\nroute=";
        for (ILane lane : route) {
            res += lane.GetId() + ";";
        }
        return res;
    }
}