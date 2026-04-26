package main.java.models.objects.road;

import main.java.models.interfaces.IVehicle;
import main.java.models.objects.Console;

/**
 * Egy alagútban futó sávot modellez a rendszerben.
 */
public class TunnelLane extends LaneBase{
   public TunnelLane(Intersection s, Intersection e) {
    super(s,e);
    Console.print("\t!<<create>>TunnelLane");
   }

    @Override
    public String toString() {
        String res = "L";
        res += "\nid=" + GetId();
        res += "\ntype=TunnelLane";
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
