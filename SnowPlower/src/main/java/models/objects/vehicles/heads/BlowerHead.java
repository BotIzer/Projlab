package main.java.models.objects.vehicles.heads;
import java.util.logging.Logger;
public class BlowerHead extends AttachmentBase {
    static Logger logger = Logger.getLogger(BlowerHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlow plow) {
        logger.info("-> BlowerHead.Clean(ILane lane, SnowPlow plow)");
        logger.info("<- BlowerHead.Clean(ILane lane, SnowPlow plow)");
    }
}