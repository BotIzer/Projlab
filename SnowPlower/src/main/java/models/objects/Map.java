package main.java.models.objects;

import java.util.List;
import java.util.logging.Logger;
import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;

public class Map {
    static Logger console = Logger.getLogger(Map.class.getName());
    private List<Road> roads;
    private List<Intersection> intersections;
    private List<IVehicle> vehicles;

    public void addRoad(Road road) {
        console.info("->Map.addRoad(Road road)");
    }

    public void addIntersections(Intersection intersection) {
        console.info("->Map.addIntersections(Intersection intersection)");
    }

    public void addVehicle(IVehicle vehicle) {
        console.info("->Map.addVehicle(IVehicle vehicle)");
    }
}
