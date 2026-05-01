package main.java.models.objects.road;
import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.*;
import main.java.models.objects.Console;

/**
 * Absztrakt alapsztályként elvégzi a specifikus sávok közös, mindennapi adminisztrációját.
 */
public abstract class LaneBase implements ILane {
    protected int id;
    protected Intersection start;
    protected Intersection end;
    protected List<IVehicle> vehicles;
    protected enum State{CLEAN, SNOWY, SNOWY_DEEP, ICY, BROKEN_ICE, BLOCKED, GRAVELED}
    State state;

    protected LaneBase(Intersection s, Intersection e) {
        vehicles = new ArrayList<>();
        start = s;
        end = e;
    }

    //@Override
    public boolean enterVehicle(IVehicle v) {
        Console.print("->LaneBase.enterVehicle(v)");
        vehicles.add(v);
        Console.print("<-LaneBase.enterVehicle(v)");
        return true;
    }

    @Override
    public boolean exitVehicle(IVehicle v) {
        Console.print("->LaneBase.exitVehicle(v)");
        vehicles.remove(v);
        Console.print("<-LaneBase.exitVehicle(v)");
        return true;
    }

    @Override
    public boolean changeState(String ns) {
        Console.print("->LaneBase.changeState(" + ns + ")");
        Console.print("<-LaneBase.changeState(" + ns + "): true");
        return true;
    }

    @Override
    public Intersection getStart() { return start; }

    @Override
    public Intersection getEnd() { return end; }

    @Override
    public boolean clear() {
        Console.print("->LaneBase.clear()");
        Console.print("<-LaneBase.clear()");
        return true;
    }
    @Override
    public String toList() {
        return Integer.toString(id);
    }
    /**
     * Részletes listázó segédfüggvény 
     * @param roadId road idje
     * @return printState formátumú szöveg
     */
    @Override
    public String printLong(int roadId) {
        StringBuilder res = new StringBuilder("Lane");
        res.append(id)
           .append(": ")
           .append(roadId)
           .append(", ")
           .append(start.toList())
           .append("-")
           .append(end.toList())
           .append(", ")
           .append(state.toString());
        for (IVehicle vehicle : vehicles) {
            res.append("\n\t")
                .append(vehicle.toList());
        }
        return res.toString();
    }
}
