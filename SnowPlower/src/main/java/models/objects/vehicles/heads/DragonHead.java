package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Egy extrém hatékonyságú tisztítófej (pl. jégolvasztó).
 */
public class DragonHead extends AttachmentBase {
    private int kerosene;
    private double amountPerSegment = 10.0; 
    public DragonHead(int id) {
        super(id, 500); 
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> DragonHead.Clean(ILane lane, SnowPlower plow)");
        
        // TDA: A fej felszólítja a hókotrót, hogy használja fel a kerozint.
        // Ha a hókotró ezt megteszi (true), a fej utasítja a sávot, hogy tisztuljon meg.
        if (plow.ConsumeBioKerosene(amountPerSegment)) {
            lane.clear();
        }
        
        Console.print("\t\t\t\t<- DragonHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String print() {
        return "DragonHead";
    }
}
