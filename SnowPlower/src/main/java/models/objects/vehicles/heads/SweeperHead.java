package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * A friss hó vagy feltört jég seprésére tervezett kefés tisztítófej.
 */
public class SweeperHead extends AttachmentBase {
    public SweeperHead(int id) {
        super(id, 50); // Ár a 8.1.6 Shop specifikáció alapján[cite: 1].
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> SweeperHead.Clean(ILane lane, SnowPlower plow)");
        lane.clear();
        Console.print("\t\t\t\t<- SweeperHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String print() {
        return "SweeperHead";
    }
}