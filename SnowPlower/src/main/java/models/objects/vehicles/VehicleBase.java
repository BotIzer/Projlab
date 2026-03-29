package main.java.models.objects.vehicles;
import main.java.models.interfaces.*;
import main.java.models.objects.Console;
import main.java.models.objects.road.Intersection;

/**
 * Minden jármű absztrakt alaposztálya, amely definiálja az alapvető mozgási képességeket és a környezettel való kapcsolatot.
 */
public abstract class VehicleBase implements IVehicle {
    protected double CurrentPosition;
    protected ILane lane;
    protected double baseSpeed;

    protected VehicleBase(double bs){
        CurrentPosition = 0.0;
        baseSpeed = bs;
    }

    @Override
    public void Move()
    {
        Console.print("-> VehicleBase.Move()");
        Console.print("<- VehicleBase.Move(): void");
    }

    @Override
    public void Stop()
    {
        Console.print("-> VehicleBase.Stop()");
        Console.print("<- VehicleBase.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        Console.print("-> VehicleBase.Slipping()");
        Console.print("<- VehicleBase.Slipping(): void");
    }

    @Override
    public void SetRoute(Intersection start, Intersection end)
    {
        Console.print("-> VehicleBase.SetRoute(Intersection start, Intersection end)");
        Console.print("<- VehicleBase.SetRoute(Intersection start, Intersection end): void");
    }
    public void setLane(ILane l){
        lane = l;
    }
}
