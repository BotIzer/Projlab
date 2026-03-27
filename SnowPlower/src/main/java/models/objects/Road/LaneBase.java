package main.java.models.objects.road;
import java.util.logging.Logger;
import java.util.List;
import main.java.models.interfaces.*;

public abstract class LaneBase implements ILane {
    protected static Logger logger = Logger.getLogger(LaneBase.class.getName());

    protected Intersection start;
    protected Intersection end;
    protected List<IVehicle> vehicles;
    protected enum state{CLEAN, SNOWY, SNOWY_DEEP, BROKEN_ICE}

    @Override
    public boolean enterVehicle(IVehicle v) {
        logger.info("->LaneBase.enterVehicle(IVehicle v)");
        logger.info("<-LaneBase.enterVehicle(IVehicle v)");
        return true;
    }

    @Override
    public boolean exitVehicle(IVehicle v) {
        logger.info("->LaneBase.exitVehicle(IVehicle v)");
        logger.info("<-LaneBase.exitVehicle(IVehicle v)");
        return true;
    }

    @Override
    public boolean changeState() {
        logger.info("->LaneBase.changeState()");
        logger.info("<-LaneBase.changeState()");
        return true;
    }

    @Override
    public boolean clear() {
        logger.info("->LaneBase.clear()");
        logger.info("<-LaneBase.clear()");
        return true;
    }
}
