package main.java.models.objects.vehicles.heads;
import java.util.Map;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * A hókotróra szerelhető speciális fej, amely légfúvással távolítja el a könnyebb havat vagy feltört jeget.
 */
public class BlowerHead extends AttachmentBase {
    protected BlowerHead(Map<String, String> data){
        super(data);
    }
    public BlowerHead(){
        super();
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> BlowerHead.Clean(ILane lane, SnowPlower plow)");
        if ("SNOWY_DEEP".equals(lane.getState())) {
            lane.changeState("SNOWY");
        }
        Console.print("\t\t\t\t<- BlowerHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String print() {
        return "BlowerHead";
    }
}