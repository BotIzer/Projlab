package main.java.models.objects.vehicles;
import java.util.List;
import java.util.logging.Logger;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;

public class Bus extends VehicleBase {
    private String LineName;
    private List<Road> line;
    static Logger logger = Logger.getLogger(Bus.class.getName());
    /**
     * A Bus osztály konstruktora.
     * @param lineName A járat neve.
     * @param route Az útvonalat alkotó utak listája.
     */
    public Bus(String lineName, List<Road> route) {
        this.LineName = lineName;
        this.line = route;
    }
    public void Move() {
        logger.info("-> Bus.Move()");
        logger.info("<- Bus.Move()");
    }
    public void Stop() {
        logger.info("-> Bus.Stop()");
        logger.info("<- Bus.Stop()");
    }
    public void Slipping() {
        logger.info("-> Bus.Slipping()");
        logger.info("<- Bus.Slipping()");
    }
    public void SetRoute(Intersection start, Intersection end) {
        logger.info("-> Bus.SetRoute(Intersection start, Intersection end)");
        logger.info("<- Bus.SetRoute(Intersection start, Intersection end)");
    }
}