package main.java.models.objects.vehicles.heads;

import java.util.logging.Logger;
public class IceBreakerHead extends AttachmentBase {
    static Logger logger = Logger.getLogger(IcebreakerHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlow plow) {
        logger.info("-> IcebreakerHead.Clean(ILane lane, SnowPlow plow)");
        logger.info("<- IcebreakerHead.Clean(ILane lane, SnowPlow plow)");
    }
}