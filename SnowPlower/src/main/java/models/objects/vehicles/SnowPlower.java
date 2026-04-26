package main.java.models.objects.vehicles;
import java.util.List;
import main.java.models.interfaces.ICleaning;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.vehicles.heads.BlowerHead;
import main.java.models.objects.vehicles.heads.IceBreakerHead;

import java.util.ArrayList;

/**
 * Speciális munkagép (hókotró), amely a sávok tisztítását végzi a felszerelt fejek segítségével.
 */
public class SnowPlower extends VehicleBase {
    
    private List<ICleaning> heads = new ArrayList<>();
    private ICleaning currentHead;

    public SnowPlower(double bs){
        super(bs);
        Console.print("\t!<<create>>SnowPlower");
        heads.add(new BlowerHead());
        heads.add(new IceBreakerHead());
        currentHead = heads.get(0);
    }

    public void attach(ICleaning newHead){
        Console.print("\t-> SnowPlower.attach(newHead)");
        heads.add(newHead);
        Console.print("\t<- SSnowPlower.attach(newHead)");
    }

    public void ChangeAttachment(ICleaning head) {
        Console.print("\t-> SnowPlower.ChangeAttachment(head)");
        currentHead = head;
        Console.print("\t<- SnowPlower.ChangeAttachment(head)");
    }

    /**
     * Aktiválja a tisztítási folyamatot az aktuális sávon a jelenlegi fejjel.
     */
    public void PerformCleaning() {
        Console.print("\t\t\t-> SnowPlower.PerformCleaning()");
        currentHead.Clean(lane, null);
        Console.print("\t\t\t<- SnowPlower.PerformCleaning()");
    }

    /**
     * Csökkenti a sókészletet.
     * @return true, ha volt elég só, különben false.
     */
    public boolean ConsumeSalt(double amount) {
        Console.print("\t-> SnowPlower.ConsumeSalt(amount)");
        Console.print("\t<- SnowPlower.ConsumeSalt(amount)");
        return true;
    }

    /**
     * Üzemanyagot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kerozin, különben false.
     */
    public boolean ConsumeBioKerosene(double amount) {
        Console.print("\t-> SnowPlower.ConsumeBioKerosene(amount)");
        Console.print("\t<- SnowPlower.ConsumeBioKerosene(amount)");
        return true;
    }

    @Override
    public void Move() {
        Console.print("\t\t-> SnowPlower.Move()");
        PerformCleaning();
        Console.print("\t\t<- SnowPlower.Move()");
    }

    @Override
    public void Stop() {
        Console.print("\t-> SnowPlower.Stop()");
        Console.print("\t<- SnowPlower.Stop()");
     }

    @Override
    public void Slipping() { 
        Console.print("\t-> SnowPlower.Slipping()");
        Console.print("\t<- SnowPlower.Slipping()");
     }

    @Override
    public void SetRoute(Intersection start, Intersection end) {
        Console.print("\t-> SnowPlower.SetRoute(start, end)");
        Move();
        Console.print("\t<- SnowPlower.SetRoute(start, end)");
    }

    @Override
    public String toString() {
        String res = "V";
        res += "\nid=" + id;
        res += "\ntype=SnowPlower";
        res += "\ncurrentPosition=" + currentPosition;
        res += "\nlane=" + lane.GetId();
        res += "\nbaseSpeed=" + baseSpeed;
        res += "\nroute=";
        for (ILane lane : route) {
            res += lane.GetId() + ";";
        }
        for (ICleaning head : heads) {
            res += head.toString();
        }
        return res;
    }
}