package main.java.models.objects.vehicles.heads;
import main.java.models.objects.vehicles.SnowPlower;
import java.util.logging.Logger;
public class SalterHead extends AttachmentBase {
    private int saltStorage; 
    private double amountPerSegment; 
    static Logger logger = Logger.getLogger(SalterHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        logger.info("-> SalterHead.Clean(ILane lane, SnowPlower plow)");
        logger.info("<- SalterHead.Clean(ILane lane, SnowPlower plow)");
    }
}