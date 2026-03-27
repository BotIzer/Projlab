package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.vehicles.SnowPlower;
import java.util.logging.Logger;
public class BlowerHead extends AttachmentBase {
    static Logger logger = Logger.getLogger(BlowerHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        logger.info("-> BlowerHead.Clean(ILane lane, SnowPlower plow)");
        logger.info("<- BlowerHead.Clean(ILane lane, SnowPlower plow)");
    }
}