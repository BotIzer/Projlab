package main.java.models.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import main.java.models.interfaces.*;
import main.java.models.objects.road.*;
import main.java.models.objects.vehicles.Car;
import main.java.models.objects.vehicles.VehicleBase;
import main.java.models.objects.vehicles.heads.AttachmentBase;

/**
 * A jatekbeli uthaloztot es kornyezetet reprezentalo kozponti osztaly.
 */
public class Map implements IObservable {
    private List<Road> roads;
    private List<Intersection> intersections;
    private List<IVehicle> vehicles;
    private static final Random rand = new Random();

    // Observer support
    private final List<IViewObserver> observers = new ArrayList<>();
    @Override public void addObserver(IViewObserver o)    { observers.add(o); }
    @Override public void removeObserver(IViewObserver o) { observers.remove(o); }
    @Override public void notifyObservers() {
        for (IViewObserver o : new ArrayList<>(observers)) o.update(this);
    }

    public List<ILane> getAllLanes() {
        return roads.stream()
            .flatMap(r -> r.getLanes().stream())
            .collect(Collectors.toList());
    }
    public List<Road> getRoads() { return roads; }

    public List<Intersection> getIntersections() {
        return intersections;
    }

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
        if (vehicle instanceof VehicleBase vb) {
            vb.setMap(this);
        }
        Console.print("<-Map.addVehicle(vehicle)");
    }

    public void listVehicles(){
        for (int i = 0; i < vehicles.size(); i++) {
            String out = "(" + i + ")" + " " + vehicles.get(i).getClass().getSimpleName();
            Console.print(out);
        }
    }

    public List<Intersection> determineRoute(List<Integer> ids){
        ArrayList<Intersection> route = new ArrayList<>();
        for (Integer id : ids) {
            if ((id > intersections.size()-1) || id < 0) {
                route.clear();
                return route;
            }
            route.add(intersections.get(id));
        }
        return route;
    }

    public void clear(){
        roads.clear();
        vehicles.clear();
        intersections.clear();
        VehicleBase.reset();
        Intersection.reset();
        Road.reset();
        LaneBase.reset();
        AttachmentBase.reset();
    }

    /**
     * Szimulacioس ciklus sorrendje:
     *  1. Minden jarmu mozog
     *  2. BLOCKED visszaszamlalo tick + veletlen havazas
     *  3. Auto-route Cars-oknak ha megalltak es a savjuk mar szabad
     *  4. Nezet ertesitese
     */
    public void loop(){
        Console.print("-> Map.loop()");

        // 1. Mozgas
        for (IVehicle vehicle : new ArrayList<>(vehicles)) {
            vehicle.Move();
        }

        // 2. Savok tickelese es havazas (~2% per sav)
        for (Road r : roads) {
            for (ILane lane : r.getLanes()) {
                lane.tickBlocked();
                // Havazas csak CLEAN savokon (ne irja felul a GRAVELED/ICY stb. allapotot)
                if ("CLEAN".equals(lane.getState()) && rand.nextInt(100) < 2) {
                    lane.changeState("SNOWY");
                }
            }
        }

        // 3. Auto-route: Cars ujrainditas ha megalltak es savjuk mar nem BLOCKED
        if (intersections.size() >= 2) {
            for (IVehicle vehicle : vehicles) {
                if (!(vehicle instanceof Car car)) continue;
                if (!car.getRoute().isEmpty()) continue;

                ILane currentLane = car.getLane();
                if (currentLane == null) continue;

                // Varjunk ha meg BLOCKED sávon all
                if ("BLOCKED".equals(currentLane.getState())) continue;

                // Utvonal keresese a sav vegerol; ha zsákutca, a sav elejerol
                boolean assigned = assignRandomRoute(car, currentLane.getEnd());
                if (!assigned) {
                    assignRandomRoute(car, currentLane.getStart());
                }
            }
        }

        // 4. Nezet ertesitese
        notifyObservers();
    }

    /**
     * Veletlen celutvonalat keres az autonak a megadott kezdoponttol.
     * Kizarja a BLOCKED elso savval rendelkezo utvonalakat.
     */
    private boolean assignRandomRoute(Car car, Intersection from) {
        if (from == null) return false;
        int maxAttempts = intersections.size() * 3;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            Intersection to = intersections.get(rand.nextInt(intersections.size()));
            if (to == from) continue;
            List<ILane> newRoute = findRoute(from, to);
            if (newRoute == null || newRoute.isEmpty()) continue;
            if ("BLOCKED".equals(newRoute.get(0).getState())) continue;
            car.SetRoute(java.util.List.of(from, to));
            return true;
        }
        return false;
    }

    public List<IVehicle> getVehicles(){ return vehicles; }

    public void repairConnections() {
        for (Road road : roads) {
            for (ILane lane : road.getLanes()) {
                lane.getStart().addRoad(road);
                lane.getEnd().addRoad(road);
            }
        }
    }

    public List<ILane> findRoute(Intersection start, Intersection end) {
        return Dijkstra.dijkstra(start, end);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        StringBuilder roadString = new StringBuilder();
        for (Intersection intersection : intersections) {
            res.append("\n");
            res.append(intersection.toString());
        }
        for (Road road : roads) {
            roadString.append("\n");
            roadString.append(road.toString());
            for (ILane lane : road.getLanes()) {
                res.append("\n");
                res.append(lane.toString());
            }
        }
        for (IVehicle vehicle : vehicles) {
            res.append("\n");
            res.append(vehicle.toString());
        }
        res.append(roadString);
        return res.toString();
    }

    public String print(){
        StringBuilder res = new StringBuilder();
        res.append("Map:")
           .append("\n\troads: ");
        for (Road road : roads) {
            for (ILane lane : road.getLanes()) {
               res.append(lane.toList()).append(", ");
            }
        }
        res.append("\n\tintersections: ");
        for (Intersection intersection : intersections) {
            res.append(intersection.toList()).append(", ");
        }
        res.append("\n\tvehicles: ");
        for (IVehicle vehicle : vehicles) {
            res.append(vehicle.toList()).append(", ");
        }
        return res.toString();
    }

    public String printLong(){
        StringBuilder res = new StringBuilder();
        res.append(print());
        for (Road road : roads) {
            res.append(road.printLong());
        }
        for (IVehicle vehicle : vehicles) {
            res.append("\n");
            res.append(vehicle.printLong());
        }
        return res.toString();
    }

    public String printInterSections(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < intersections.size(); i++) {
            String[] tmp = intersections.get(i).toString().split("\n");
            res.append("\n").append("(").append(i).append(") ")
               .append(tmp[1]).append(" ").append(tmp[2]);
        }
        res.append("\n").append("(x) End selection");
        return res.toString();
    }

    public void initGeneral(){
        ArrayList<ILane> lanes = (ArrayList<ILane>)roads.get(0).getLanes();
        for (int i = 0; i < vehicles.size(); i++) {
            ILane lane = lanes.get(i);
            VehicleBase vehicle = (VehicleBase)vehicles.get(i);
            lane.enterVehicle(vehicle);
            vehicle.setLane(lane);
        }
    }

    public void initIcy(){
        ArrayList<ILane> lanes = (ArrayList<ILane>)roads.get(0).getLanes();
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
