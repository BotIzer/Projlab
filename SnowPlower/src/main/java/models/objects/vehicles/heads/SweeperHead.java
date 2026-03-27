package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.ILane;
import main.java.models.objects.vehicles.SnowPlower;
import java.util.logging.Logger;
public class SweeperHead extends AttachmentBase {
    static Logger logger = Logger.getLogger(SweeperHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        logger.info("-> SweeperHead.Clean(ILane lane, SnowPlower plow)");
        logger.info("<- SweeperHead.Clean(ILane lane, SnowPlower plow)");
    }
}