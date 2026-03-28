package main.java.models.objects.road;
import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.*;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.*;;

public abstract class LaneBase implements ILane {

    protected Intersection start;
    protected Intersection end;
    protected List<IVehicle> vehicles;
    protected enum state{CLEAN, SNOWY, SNOWY_DEEP, BROKEN_ICE}

    protected LaneBase(Intersection s, Intersection e){
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
    public boolean clear() {
        Console.print("->LaneBase.clear()");
        Console.print("<-LaneBase.clear()");
        return true;
    }
}
