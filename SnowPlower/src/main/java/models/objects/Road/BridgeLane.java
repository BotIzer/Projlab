package main.java.models.objects.road;

public class BridgeLane extends LaneBase {
    @Override
    public boolean clear() {
        logger.info("->BridgeLane.clear()");
        logger.info("<-BridgeLane.clear()");
        return true;
    }
}
