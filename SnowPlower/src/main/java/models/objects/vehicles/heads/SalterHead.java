package main.java.models.objects.vehicles.heads;

import java.util.logging.Logger;
public class SalterHead extends AttachmentBase {
    private int saltStorage; 
    private double amountPerSegment; 
    static Logger logger = Logger.getLogger(SalterHead.class.getName());
    @Override
    public void Clean(ILane lane, SnowPlow plow) {
        logger.info("-> SalterHead.Clean(ILane lane, SnowPlow plow)");
        logger.info("<- SalterHead.Clean(ILane lane, SnowPlow plow)");
    }
}