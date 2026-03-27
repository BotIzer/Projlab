package main.java.models.objects.vehicles;
import java.util.List;
import main.java.models.interfaces.ICleaning;
import main.java.models.objects.road.Intersection;

import java.util.ArrayList;
import java.util.logging.Logger;


public class SnowPlower extends VehicleBase {
    
    private List<ICleaning> head = new ArrayList<>();
    private ICleaning currentHead;
    static Logger logger = Logger.getLogger(SnowPlower.class.getName());

    public void ChangeAttachment(ICleaning head) {
        logger.info("-> SnowPlower.ChangeAttachment(ICleaning head)");
        logger.info("<- SnowPlower.ChangeAttachment(ICleaning head)");
    }

    /**
     * Aktiválja a tisztítási folyamatot az aktuális sávon a jelenlegi fejjel.
     */
    public void PerformCleaning() {
        logger.info("-> SnowPlower.PerformCleaning()");
        logger.info("<- SnowPlower.PerformCleaning()");
    }

    /**
     * Csökkenti a sókészletet.
     * @return true, ha volt elég só, különben false.
     */
    public boolean ConsumeSalt(double amount) {
        logger.info("-> SnowPlower.ConsumeSalt(double amount)");
        logger.info("<- SnowPlower.ConsumeSalt(double amount)");
        return true;
    }

    /**
     * Üzemanyagot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kerozin, különben false.
     */
    public boolean ConsumeBioKerosene(double amount) {
        logger.info("-> SnowPlower.ConsumeBioKerosene(double amount)");
        logger.info("<- SnowPlower.ConsumeBioKerosene(double amount)");
        return true;
    }

    @Override
    public void Move() {
        logger.info("-> SnowPlower.Move()");
        logger.info("<- SnowPlower.Move()");
    }

    @Override
    public void Stop() {
        logger.info("-> SnowPlower.Stop()");
        logger.info("<- SnowPlower.Stop()");
     }

    @Override
    public void Slipping() { 
        logger.info("-> SnowPlower.Slipping()");
        logger.info("<- SnowPlower.Slipping()");
     }

    @Override
    public void SetRoute(Intersection start, Intersection end) {
        logger.info("-> SnowPlower.SetRoute(start, end)");
        logger.info("<- SnowPlower.SetRoute(start, end)");
    }
}