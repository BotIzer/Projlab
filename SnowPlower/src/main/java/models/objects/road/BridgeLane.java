package main.java.models.objects.road;

import java.util.Map;

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
    //Fileból betöltés
    public BridgeLane(Map<String, String> data){
        super(data);
    }

    @Override
    public boolean clear() {
        Console.print("->BridgeLane.clear()");
        boolean res = super.clear();
        Console.print("<-BridgeLane.clear():true");
        return res;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("L"); 
        res.append("\nid=" + id);
        res.append("\ntype=BridgeLane");
        res.append("\nstart=" + start.toList());
        res.append("\nend=" + end.toList());
        res.append("\nvehicles=");
        for (IVehicle vehicle : vehicles) {
            res.append(vehicle.toList());
        }
        res.append("\nstate=" + state.toString());
        return res.toString();
    }
}
