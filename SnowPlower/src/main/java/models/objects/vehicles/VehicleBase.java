package main.java.models.objects.vehicles;
import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;

import java.util.logging.Logger;

public abstract class VehicleBase implements IVehicle
{
    private static final Logger logger = Logger.getLogger(VehicleBase.class.getName());

    protected double CurrentPosition;
    protected ILane lane;
    protected double baseSpeed;

    @Override
    public void Move()
    {
        logger.info("-> VehicleBase.Move()");
        logger.info("<- VehicleBase.Move(): void");
    }

    @Override
    public void Stop()
    {
        logger.info("-> VehicleBase.Stop()");
        logger.info("<- VehicleBase.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        logger.info("-> VehicleBase.Slipping()");
        logger.info("<- VehicleBase.Slipping(): void");
    }

    @Override
    public void SetRoute(Intersection start, Intersection end)
    {
        logger.info("-> VehicleBase.SetRoute(Intersection start, Intersection end)");
        logger.info("<- VehicleBase.SetRoute(Intersection start, Intersection end): void");
    }

}
