package main.java.models.objects.vehicles.heads;
import java.util.Map;
import java.util.Map.Entry;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Egy extrém hatékonyságú tisztítófej (pl. jégolvasztó).
 */
public class DragonHead extends AttachmentBase {
    //TODO add base amount
    private int kerosene;
    private double amountPerSegment; 
    public DragonHead(){
        super();
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> DragonHead.Clean(ILane lane, SnowPlower plow)");
        if ("ICY".equals(lane.getState())) {
            lane.changeState("CLEAN");
        }
        Console.print("\t\t\t\t<- DragonHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(super.toString());
        res.append("\nkerosene=").append(kerosene)
           .append("\namountPerSegment=").append(amountPerSegment);
        return res.toString();
    }
    @Override
    public String print() {
        return "DragonHead";
    }
    protected DragonHead(Map<String, String> data){
        super(data);
        for (Entry<String, String> line : data.entrySet()) {
            switch (line.getValue()) {
                case "kerosene" -> Integer.parseInt(line.getValue());
                case "amountPerSegment" -> Double.parseDouble(line.getValue());
                default -> {break;}
            }
        }
    }
}
