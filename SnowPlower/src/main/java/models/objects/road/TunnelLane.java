package main.java.models.objects.road;

import java.util.Map;

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
    //Fileból betöltés
    public TunnelLane(Map<String, String> data){
        super(data);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("L");
        res.append("\nid=" + id);
        res.append("\ntype=TunnelLane");
        res.append("\nstart=" + start.toList()); 
        res.append("\nend=" + end.toList()); 
        res.append("\nvehicles="); 
        for (IVehicle vehicle : vehicles) {
            res.append(vehicle.toList() );
            res.append(";");
        }
        res.append("\nstate=" );
        res.append(state.toString());
        return res.toString();
    }
}
