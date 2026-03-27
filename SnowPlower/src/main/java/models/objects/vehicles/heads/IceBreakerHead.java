package main.java.models.objects.vehicles.heads;
import main.java.models.objects.vehicles.SnowPlower;
import java.util.logging.Logger;
public class IceBreakerHead extends AttachmentBase {
    static Logger logger = Logger.getLogger(IceBreakerHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        logger.info("-> IcebreakerHead.Clean(ILane lane, SnowPlower plow)");
        logger.info("<- IcebreakerHead.Clean(ILane lane, SnowPlower plow)");
    }
}