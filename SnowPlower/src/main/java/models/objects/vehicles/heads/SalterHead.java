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
     private int saltStorage = 10; 
     private double amountPerSegment = 5.0; 
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
   @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> SalterHead.Clean(ILane lane, SnowPlower plow)");
        
        // TDA: Utasítja a hókotrót a só szórására. Ha sikeres, a jég/hó elolvad (clear).
        if (this.ConsumeSalt(amountPerSegment)) {
            lane.clear();
        }
        
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
        /**
        * Sót von le a tartályból a munka végzése során.
        * @return true, ha volt elég só, különben false.
        */
     public boolean ConsumeSalt(double amount) {
        Console.print("\t-> SnowPlower.ConsumeSalt(amount)");
        if (saltStorage >= amount) {
            saltStorage -= amount;
            Console.print("\t<- SnowPlower.ConsumeSalt(amount) [SUCCESS]");
            return true;
        }
        Console.print("\t<- SnowPlower.ConsumeSalt(amount) [FAIL - No Salt]");
        return false;
    }
}