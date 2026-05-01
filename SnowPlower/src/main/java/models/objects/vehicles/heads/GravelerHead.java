package main.java.models.objects.vehicles.heads;
import java.util.Map;
import java.util.Map.Entry;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Egy extrém hatékonyságú tisztítófej (pl. jégolvasztó).
 */
public class GravelerHead extends AttachmentBase {
    //TODO add base amount
    private int gravel = 10;
    private double amountPerSegment = 5.0; 
    public GravelerHead(){
        super();
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> DragonHead.Clean(ILane lane, SnowPlower plow)");
        
        // TDA: A fej felszólítja a hókotrót, hogy használja fel a kerozint.
        // Ha a hókotró ezt megteszi (true), a fej utasítja a sávot, hogy tisztuljon meg.
        if (this.ConsumeGravel(amountPerSegment)) {
            lane.clear();
        }
        
        Console.print("\t\t\t\t<- GravelerHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(super.toString());
        res.append("\ngravel=").append(gravel)
           .append("amountPerSegment=").append(amountPerSegment);
        return res.toString();
    }
    @Override
    public String print() {
        return "GravelerHead";
    }
    protected GravelerHead(Map<String, String> data){
        super(data);
        for (Entry<String, String> line : data.entrySet()) {
            switch (line.getValue()) {
                case "gravel" -> Integer.parseInt(line.getValue());
                case "amountPerSegment" -> Double.parseDouble(line.getValue());
                default -> {break;}
            }
        }
    }
    /**
     * Kavicsot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kavics, különben false.
     */
    
    public boolean ConsumeGravel(double amount) {
        Console.print("\t-> SnowPlower.ConsumeGravel(amount)");
        if (gravel >= amount) {
            gravel -= amount;
            Console.print("\t<- SnowPlower.ConsumeGravel(amount) [SUCCESS]");
            return true;
        }
        Console.print("\t<- SnowPlower.ConsumeGravel(amount) [FAIL - No Gravel]");
        return false;
    }
}
