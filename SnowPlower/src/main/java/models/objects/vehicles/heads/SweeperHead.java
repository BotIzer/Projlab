package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * A friss hó vagy feltört jég seprésére tervezett kefés tisztítófej.
 */
public class SweeperHead extends AttachmentBase {
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> SweeperHead.Clean(ILane lane, SnowPlower plow)");
        lane.clear();
        Console.print("\t\t\t\t<- SweeperHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String toString() {
        String res = "H";
        res += "id=" + id;
        res += "\ntype=SweeperHead";
        return res;
    }
}