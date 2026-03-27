package main.java.models.objects.Road;

public class RoadLane extends LaneBase {
    @Override
    public boolean clear() {
        logger.info("->RoadLane.clear()");
        logger.info("<-RoadLane.clear()");
        return true;
    }
}
