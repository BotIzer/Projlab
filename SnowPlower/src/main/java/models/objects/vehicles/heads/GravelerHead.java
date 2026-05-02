package main.java.models.objects.vehicles.heads;

import java.util.Map;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Kavicszózó fej: jeges vagy feltört jeges sávon homokot/kavicsot szór,
 * hogy javítsa a tapadást (GRAVELED állapot).
 */
public class GravelerHead extends AttachmentBase {

    public GravelerHead() {
        super();
    }

    protected GravelerHead(Map<String, String> data) {
        super(data);
    }

    /** ICY, BROKEN_ICE → GRAVELED */
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> GravelerHead.Clean(ILane lane, SnowPlower plow)");
        String s = lane.getState();
        if ("ICY".equals(s) || "BROKEN_ICE".equals(s)) {
            lane.changeState("GRAVELED");
        }
        Console.print("\t\t\t\t<- GravelerHead.Clean(ILane lane, SnowPlower plow)");
    }

    @Override
    public String print() {
        return "GravelerHead";
    }
}
