package main.java.models.objects.road;

import main.java.models.interfaces.IVehicle;
import main.java.models.objects.Console;

/**
 * A hagyományos, nyílt felszíni útsávokat képviseli.
 */
public class RoadLane extends LaneBase {

    public RoadLane(Intersection s, Intersection e) {
        super(s, e);
        Console.print("\t!<<create>>RoadLane");
    }

    @Override
    public boolean clear() {
        Console.print("->RoadLane.clear()");
        Console.print("<-RoadLane.clear()");
        return true;
    }
    @Override
    public String toString() {
        String res = "L";
        res += "\nid=" + GetId();
        res += "\ntype=RoadLane";
        res += "\nstart=" + start.GetId();
        res += "\nend=" + end.GetId();
        res += "\nvehicles=";
        for (IVehicle vehicle : vehicles) {
            res += vehicle.GetId() + ";";
        }
        res += "\nstate=" + state.toString();
        return res;
    }
}
