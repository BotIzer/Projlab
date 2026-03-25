package main.java.models.objects.vehicles.heads;
import java.util.logging.Logger;
public class SweeperHead extends AttachmentBase {
    static Logger logger = Logger.getLogger(SweeperHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlow plow) {
        logger.info("-> SweeperHead.Clean(ILane lane, SnowPlow plow)");
        logger.info("<- SweeperHead.Clean(ILane lane, SnowPlow plow)");
    }
}