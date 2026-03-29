package main.java.models.objects.vehicles;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.road.Intersection;

/**
 * A város normál forgalmát adó, önálló célokkal rendelkező jármű.
 */
public class Car extends VehicleBase
{

    public Car(double bs){
        super(bs);
        Console.print("\t!<<create>>Car");
    }

    @Override
    public void Move()
    {
         Console.print("-> Car.Move()");
         Console.print("<- Car.Move(): void");
    }

    @Override
    public void Stop()
    {
        Console.print("-> Car.Stop()");
        Console.print("<- Car.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        Console.print("-> Car.Slipping()");
        lane.changeState("blocked");
        Console.print("<- Car.Slipping(): void");
    }

    @Override
    public void SetRoute(Intersection start, Intersection end)
    {
        Console.print("-> Car.SetRoute(Intersection start, Intersection end)");
        Move();
        Console.print("<- Car.SetRoute(Intersection start, Intersection end)");
    }
}
