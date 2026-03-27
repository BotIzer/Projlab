package main.java.models.objects.vehicles;
import java.util.List;
import main.java.models.interfaces.ICleaning;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.vehicles.heads.BlowerHead;
import main.java.models.objects.vehicles.heads.IceBreakerHead;

import java.util.ArrayList;


public class SnowPlower extends VehicleBase {
    
    private List<ICleaning> heads = new ArrayList<>();
    private ICleaning currentHead;

    public SnowPlower(double bs){
        super(bs);
        Console.print("\t!<<create>>SnowPlower");
        heads.add(new BlowerHead());
        heads.add(new IceBreakerHead());
    
    }

    public void attach(ICleaning newHead){
        Console.print("-> SnowPlower.attach(newHead)");
        heads.add(newHead);
        Console.print("<- SSnowPlower.attach(newHead)");
    }

    public void ChangeAttachment(ICleaning head) {
        Console.print("-> SnowPlower.ChangeAttachment(head)");
        currentHead = head;
        Console.print("<- SnowPlower.ChangeAttachment(head)");
    }

    /**
     * Aktiválja a tisztítási folyamatot az aktuális sávon a jelenlegi fejjel.
     */
    public void PerformCleaning() {
        Console.print("-> SnowPlower.PerformCleaning()");
        Console.print("<- SnowPlower.PerformCleaning()");
    }

    /**
     * Csökkenti a sókészletet.
     * @return true, ha volt elég só, különben false.
     */
    public boolean ConsumeSalt(double amount) {
        Console.print("-> SnowPlower.ConsumeSalt(amount)");
        Console.print("<- SnowPlower.ConsumeSalt(amount)");
        return true;
    }

    /**
     * Üzemanyagot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kerozin, különben false.
     */
    public boolean ConsumeBioKerosene(double amount) {
        Console.print("-> SnowPlower.ConsumeBioKerosene(amount)");
        Console.print("<- SnowPlower.ConsumeBioKerosene(amount)");
        return true;
    }

    @Override
    public void Move() {
        Console.print("-> SnowPlower.Move()");
        Console.print("<- SnowPlower.Move()");
    }

    @Override
    public void Stop() {
        Console.print("-> SnowPlower.Stop()");
        Console.print("<- SnowPlower.Stop()");
     }

    @Override
    public void Slipping() { 
        Console.print("-> SnowPlower.Slipping()");
        Console.print("<- SnowPlower.Slipping()");
     }

    @Override
    public void SetRoute(Intersection start, Intersection end) {
        Console.print("-> SnowPlower.SetRoute(start, end)");
        Console.print("<- SnowPlower.SetRoute(start, end)");
    }
}