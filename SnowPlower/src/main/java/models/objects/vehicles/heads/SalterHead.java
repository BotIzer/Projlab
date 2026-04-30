package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Sószóró egység, amely elolvasztja a jeget és a havat az útról.
 */
public class SalterHead extends AttachmentBase {
    private int saltStorage; 
    private double amountPerSegment = 5.0; 
    public SalterHead(int id) {
        super(id, 200); 
    }
   @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> SalterHead.Clean(ILane lane, SnowPlower plow)");
        
        // TDA: Utasítja a hókotrót a só szórására. Ha sikeres, a jég/hó elolvad (clear).
        if (plow.ConsumeSalt(amountPerSegment)) {
            lane.clear();
        }
        
        Console.print("\t\t\t\t<- SalterHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String print() {
        return "SalterHead";
    }
}