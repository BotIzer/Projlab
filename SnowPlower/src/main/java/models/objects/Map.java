package main.java.models.objects;

import java.util.List;
import java.util.logging.Logger;
import main.java.models.interfaces.*;

public class Map {
    static Logger console = Logger.getLogger(Map.class.getName());
    private List<Road> roads;
    private List<Intersection> intersections;
    private List<IVehicle> vehicles;

    public void addRoad(Road road) {
        console.info("->Map.addRoad(Road road)");
    }

    public void addIntersections(Intersection Intersection) {
        console.info("->Map.addIntersections(Intersection Intersection)");
    }

    public void addVehicle(IVehicle vehicle) {
        console.info("->Map.addVehicle(IVehicle vehicle)");
    }
}
