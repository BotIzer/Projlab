package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Mechanikai úton, zúzással töri fel a jégréteget az útról, de el nem takarítja azt.
 */
public class IceBreakerHead extends AttachmentBase {
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> IcebreakerHead.Clean(ILane lane, SnowPlower plow)");
        lane.clear();
        Console.print("\t\t\t\t<- IcebreakerHead.Clean(ILane lane, SnowPlower plow)");
    }
}
    