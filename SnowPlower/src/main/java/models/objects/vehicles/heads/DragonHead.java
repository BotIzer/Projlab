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
    private int kerosene = 10;
    private double amountPerSegment = 5.0; 
    public DragonHead(){
        super();
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> DragonHead.Clean(ILane lane, SnowPlower plow)");
        
        // TDA: A fej felszólítja a hókotrót, hogy használja fel a kerozint.
        // Ha a hókotró ezt megteszi (true), a fej utasítja a sávot, hogy tisztuljon meg.
        if (this.ConsumeBioKerosene(amountPerSegment)) {
            lane.clear();
        }
        
        Console.print("\t\t\t\t<- DragonHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(super.toString());
        res.append("\nkerosene=").append(kerosene)
           .append("amountPerSegment=").append(amountPerSegment);
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
    /**
     * Üzemanyagot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kerozin, különben false.
     */
    
    public boolean ConsumeBioKerosene(double amount) {
        Console.print("\t-> SnowPlower.ConsumeBioKerosene(amount)");
        if (kerosene >= amount) {
            kerosene -= amount;
            Console.print("\t<- SnowPlower.ConsumeBioKerosene(amount) [SUCCESS]");
            return true;
        }
        Console.print("\t<- SnowPlower.ConsumeBioKerosene(amount) [FAIL - No Kerosene]");
        return false;
    }
}
