package main.java.models.objects.vehicles.heads;
import java.util.Map;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Mechanikai úton, zúzással töri fel a jégréteget az útról, de el nem takarítja azt.
 */
public class IceBreakerHead extends AttachmentBase {
    public IceBreakerHead(){
        super();
    } 
    protected IceBreakerHead(Map<String, String> data) {
        super(data);
    }
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> IcebreakerHead.Clean(ILane lane, SnowPlower plow)");
        
        // TDA: A specifikáció alapján ez a fej NEM clear()-eli az utat, 
        // hanem feltöri a jeget. Utasítjuk a sávot az állapotváltásra.
        lane.changeState("BrokenIcy");
        //lane.clear();
        Console.print("\t\t\t\t<- IcebreakerHead.Clean(ILane lane, SnowPlower plow)");
    }
    @Override
    public String print() {
        return "IceBreakerHead";
    }
}
    