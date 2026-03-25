package main.java.models.objects.vehicles;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;


public class SnowPlower extends VehicleBase {
    
    private List<ICleaning> head = new ArrayList<>();
    private ICleaning currentHead;
    static Logger logger = Logger.getLogger(SnowPlower.class.getName());

    public void ChangeAttachment(ICleaning head) {
        logger.info("-> SnowPlow.ChangeAttachment(ICleaning head)");
        logger.info("<- SnowPlow.ChangeAttachment(ICleaning head)");
    }

    /**
     * Aktiválja a tisztítási folyamatot az aktuális sávon a jelenlegi fejjel.
     */
    public void PerformCleaning() {
        logger.info("-> SnowPlow.PerformCleaning()");
        logger.info("<- SnowPlow.PerformCleaning()");
    }

    /**
     * Csökkenti a sókészletet.
     * @return true, ha volt elég só, különben false.
     */
    public boolean ConsumeSalt(double amount) {
        logger.info("-> SnowPlow.ConsumeSalt(double amount)");
        logger.info("<- SnowPlow.ConsumeSalt(double amount)");
        return true;
    }

    /**
     * Üzemanyagot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kerozin, különben false.
     */
    public boolean ConsumeBioKerosene(double amount) {
        logger.info("-> SnowPlow.ConsumeBioKerosene(double amount)");
        logger.info("<- SnowPlow.ConsumeBioKerosene(double amount)");
        return true;
    }

    @Override
    public void Move() {
        logger.info("-> SnowPlow.Move()");
        logger.info("<- SnowPlow.Move()");
    }

    @Override
    public void Stop() {
        logger.info("-> SnowPlow.Stop()");
        logger.info("<- SnowPlow.Stop()");
     }

    @Override
    public void Slipping() { 
        logger.info("-> SnowPlow.Slipping()");
        logger.info("<- SnowPlow.Slipping()");
     }
}