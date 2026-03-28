package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Egy extrém hatékonyságú tisztítófej (pl. jégolvasztó).
 */
public class DragonHead extends AttachmentBase {
    private int kerosene;
    private double amountPerSegment; 
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> DragonHead.Clean(ILane lane, SnowPlower plow)");
        lane.clear();
        Console.print("\t\t\t\t<- DragonHead.Clean(ILane lane, SnowPlower plow)");
    }
}