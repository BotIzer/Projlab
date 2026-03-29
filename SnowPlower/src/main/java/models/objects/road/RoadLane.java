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
}
