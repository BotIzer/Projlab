import java.util.logging.Logger;

public class Car extends VehicleBase
{
    private static final Logger logger = Logger.getLogger(VehicleBase.class.getName());

    @Override
    public void Move()
    {
         logger.info("-> Car.Move()");
         logger.info("<- Car.Move(): void");
    }

    @Override
    public void Stop()
    {
        logger.info("-> Car.Stop()");
        logger.info("<- Car.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        logger.info("-> Car.Slipping()");
        logger.info("<- Car.Slipping(): void");
    }

    @Override
    public void SetRoute(Intersection start, Intersection end)
    {
        logger.info("-> Car.SetRoute(Intersection start, Intersection end)");
        logger.info("<- Car.SetRoute(Intersection start, Intersection end)");
    }
}
