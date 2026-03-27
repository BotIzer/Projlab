package main.java.models.objects.vehicles.heads;
import main.java.models.objects.vehicles.SnowPlower;
import java.util.logging.Logger;
public class DragonHead extends AttachmentBase {
    private int kerosene;
    private double amountPerSegment; 
    static Logger logger = Logger.getLogger(DragonHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        logger.info("-> DragonHead.Clean(ILane lane, SnowPlower plow)");
        logger.info("<- DragonHead.Clean(ILane lane, SnowPlower plow)");
    }
}