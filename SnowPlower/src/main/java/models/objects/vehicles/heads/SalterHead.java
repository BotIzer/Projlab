package main.java.models.objects.vehicles.heads;
import java.util.Map;
import java.util.Map.Entry;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Sószóró egység, amely elolvasztja a jeget és a havat az útról.
 */
public class SalterHead extends AttachmentBase {
    public SalterHead(){
        super();
    } 
    protected SalterHead(Map<String, String> data) {
        super(data);
        for (Entry<String, String> line : data.entrySet()) {
            switch (line.getValue()) {
                case "saltStorage" -> Integer.parseInt(line.getValue());
                case "amountPerSegment" -> Double.parseDouble(line.getValue());
                default -> {break;}
            }
        }
    }
    private int saltStorage; 
    private double amountPerSegment; 
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> SalterHead.Clean(ILane lane, SnowPlower plow)");
        lane.clear();
        Console.print("\t\t\t\t<- SalterHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String print() {
        return "SalterHead";
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(super.toString());
        res.append("\nsaltStorage=").append(saltStorage)
           .append("amountPerSegment=").append(amountPerSegment);
        return res.toString();
    }
}