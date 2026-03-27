package main.java.models.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;

public class Map {
    private List<Road> roads;
    private List<Intersection> intersections;
    private List<IVehicle> vehicles;

    public Map(){
        Console.print("\t!<<create>>Map");
        roads = new ArrayList<>();
        intersections = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    public void addRoad(Road road) {
        Console.print("->Map.addRoad(road)");
        roads.add(road);
        Console.print("<-Map.addRoad(road)");
    }

    public void addIntersections(Intersection intersection) {
        Console.print("->Map.addIntersections(intersection)");
        intersections.add(intersection);
        Console.print("<-Map.addIntersections(intersection)");
    }

    public void addVehicle(IVehicle vehicle) {
        Console.print("->Map.addVehicle(vehicle)");
        vehicles.add(vehicle);
        Console.print("<-Map.addVehicle(vehicle)");
    }
    public void initGeneral(){
        ArrayList<ILane> lanes = (ArrayList<ILane>)roads.getFirst().getLanes();
        lanes.get(0).enterVehicle(vehicles.get(0));
        lanes.get(1).enterVehicle(vehicles.get(1));
        lanes.get(2).enterVehicle(vehicles.get(2));
    }
}
