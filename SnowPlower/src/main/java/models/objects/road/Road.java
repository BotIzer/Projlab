package main.java.models.objects.road;
import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;

/**
 * Egy konkrét útszakaszt reprezentál az úthálózatban.
 */
public class Road {
    private int id;
    private List<ILane> lanes;
    private double length;

    public Road(List<ILane> ls, double len) {
        Console.print("\t!<<create>>Road");
        lanes = ls;
        length = len;
    }

    public List<ILane> getLanes(){ return lanes; }

    public List<Intersection> initGeneral(){
        ArrayList<Intersection> intersections = new ArrayList<>();
        Intersection istart = new Intersection(this);
        Intersection iend = new Intersection(this);
        intersections.add(istart);
        intersections.add(iend);
        RoadLane rl = new RoadLane(istart, iend);
        BridgeLane bl = new BridgeLane(istart, iend);
        TunnelLane tl = new TunnelLane(istart, iend);
        this.lanes.add(rl);
        this.lanes.add(bl);
        this.lanes.add(tl);
        return intersections;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("R");
        res.append("\nid=" + id)
        .append("\nlanes=");
        for (ILane lane : lanes) {
           res.append(lane.toList())
           .append(";");
        }
        res.append("\nlength=")
           .append(length);
        return res.toString();
    }
    public String toList(){
        return Integer.toString(id);
    }
    public String printLong(){
        StringBuilder res = new StringBuilder();
        for (ILane lane : lanes) {
            res.append("\n")
               .append(lane.printLong(id));
        }
        return res.toString();
    }
}
