package main.java.models.objects.vehicles.heads;
import java.util.Map;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * A friss hó vagy feltört jég seprésére tervezett kefés tisztítófej.
 */
public class SweeperHead extends AttachmentBase {
    public SweeperHead(){
        super();
    }
    protected SweeperHead(Map<String, String> data) {
        super(data);
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