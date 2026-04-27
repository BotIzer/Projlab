package main.java.models.objects.road;

import main.java.models.interfaces.IVehicle;
import main.java.models.objects.Console;

/**
 * Hídon áthaladó sávot modellez, amelynek a hagyományos utaktól eltérő, saját logikája van.
 */
public class BridgeLane extends LaneBase {

    public BridgeLane(Intersection s, Intersection e){
        super(s, e);
        Console.print("\t!<<create>>BridgeLane");
    }

    @Override
    public boolean clear() {
        Console.print("->BridgeLane.clear()");
        Console.print("<-BridgeLane.clear():true");
        return true;
    }
    @Override
    public String toString() {
        String res = "L";
        res += "\nid=" + id;
        res += "\ntype=BridgeLane";
        res += "\nstart=" + start.toList();
        res += "\nend=" + end.toList();
        res += "\nvehicles=";
        for (IVehicle vehicle : vehicles) {
            res += vehicle.toList() + ";";
        }
        res += "\nstate=" + state.toString();
        return res;
    }
}
