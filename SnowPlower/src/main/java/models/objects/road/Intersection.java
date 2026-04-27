package main.java.models.objects.road; 
import java.util.ArrayList;
import java.util.List;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;

/**
 * A forgalmi csomópontokat kezeli, ahol az utak és a sávok találkoznak.
 */
public class Intersection {
    private int id;
    private List<Road> roads;

    public Intersection(Road r){
        Console.print("\t!<<create>>Intersection");
        roads = new ArrayList<>();
        roads.add(r);
    }
    public void addRoad(Road r){
        roads.add(r);
    }
    public String toList(){
        return Integer.toString(id);
    }
    @Override
    public String toString() {
        String res = "I";
        res += "\nid=" + id;
        res += "lanes=";
        for (Road road : roads) {
            for (ILane lane : road.getLanes()) {
                res += lane.toList() + ";";
            }
        }
        return res;
    }
}
