package main.java.models.objects.vehicles;
import java.util.List;
import java.util.logging.Logger;

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
        //TODO: implement move logic for bus
        logger.info("-> Bus.Move()");
        logger.info("<- Bus.Move()");
    }
    public void Stop() {
        // TODO: implement stop logic for bus
        logger.info("-> Bus.Stop()");
        logger.info("<- Bus.Stop()");
    }
    public void Slipping() {
        //TODO: implement slipping logic for bus
        logger.info("-> Bus.Slipping()");
        logger.info("<- Bus.Slipping()");
    }
    public void SetRoute(Intersection start, Intersection end) {
        //TODO: implement route setting logic for bus
        logger.info("-> Bus.SetRoute(Intersection start, Intersection end)");
        logger.info("<- Bus.SetRoute(Intersection start, Intersection end)");
}