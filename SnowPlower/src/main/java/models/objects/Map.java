package main.java.models.objects;

import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.*;
import main.java.models.objects.road.*;
import main.java.models.objects.vehicles.VehicleBase;

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
    public void listVehicles(){
        for (int i = 0; i < vehicles.size(); i++) {
            String out = "(" + i + ")" + " " + vehicles.get(i).toString();
            Console.print(out);
        }
    }
    public void loop(){
        Console.print("-> Map.loop()");
        for (IVehicle vehicle : vehicles) {
            vehicle.Move();
        }
        for (Road r : roads) {
            for (ILane lane : r.getLanes()) {
                lane.changeState("snowy");
            }
        }
        Console.print("<- Map.loop");
    }
    public List<IVehicle> getVehicles(){return vehicles;}
    public void initGeneral(){
        ArrayList<ILane> lanes = (ArrayList<ILane>)roads.getFirst().getLanes();
        
        for (int i = 0; i < vehicles.size(); i++) {
            ILane lane = lanes.get(i);
            VehicleBase vehicle = (VehicleBase)vehicles.get(i);
            lane.enterVehicle(vehicle);
            vehicle.setLane(lane);
        }
    }
    public void initIcy(){
        ArrayList<ILane> lanes = (ArrayList<ILane>)roads.getFirst().getLanes();
        for (int i = 0; i < vehicles.size(); i++) {
            ILane lane = lanes.get(0);
            VehicleBase vehicle = (VehicleBase)vehicles.get(i);
            lane.enterVehicle(vehicle);
            vehicle.setLane(lane);
        }
        vehicles.get(2).Slipping();
        vehicles.get(2).Stop();
        vehicles.get(1).Stop();

    }
}
