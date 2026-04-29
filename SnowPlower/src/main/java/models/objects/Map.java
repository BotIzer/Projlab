package main.java.models.objects;

import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.*;
import main.java.models.objects.road.*;
import main.java.models.objects.vehicles.VehicleBase;

/**
 * A játékbeli úthálózatot és környezetet reprezentáló központi osztály.
 */
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

    /**
     * Felvesz egy új útszakaszt a térképre.
     * @param road az új út
     */
    public void addRoad(Road road) {
        Console.print("->Map.addRoad(road)");
        roads.add(road);
        Console.print("<-Map.addRoad(road)");
    }

    /**
     * Felvesz egy új kereszteződést a térképre.
     * @param intersection az új kereszteződés
     */
    public void addIntersections(Intersection intersection) {
        Console.print("->Map.addIntersections(intersection)");
        intersections.add(intersection);
        Console.print("<-Map.addIntersections(intersection)");
    }

    /**
     * Felvesz egy új járművet a térképre.
     * @param vehicle az új jármű
     */
    public void addVehicle(IVehicle vehicle) {
        Console.print("->Map.addVehicle(vehicle)");
        vehicles.add(vehicle);
        Console.print("<-Map.addVehicle(vehicle)");
    }

    /**
     * Listázza a járműveket.
     * formátum: (sorszám) Név/Tipus
     */
    public void listVehicles(){
        for (int i = 0; i < vehicles.size(); i++) {
            String out = "(" + i + ")" + " " + vehicles.get(i).getClass().getSimpleName();
            Console.print(out);
        }
    }
    /**
     * Meghatározza az érintendő végpontokat kiválasztás után
     * Console.setRoute() segédfüggvénye
     * @param ids
     * @return
     */
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

    /**
     * Végrehajt egy szimulációs ciklust a térképen.
     */
    //TODO implement simulation
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

    public List<IVehicle> getVehicles(){ return vehicles; }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        StringBuilder roadString = new StringBuilder();
        for (Intersection intersection : intersections) {
            res.append(intersection.toString());
        }
        for (Road road : roads) {
            roadString.append(road.toString());
            for (ILane lane : road.getLanes()) {
                res.append(lane.toString());
            } 
        }
        for (IVehicle vehicle : vehicles) {

            res.append(vehicle.toString());
        }
        res.append(roadString);
        return res.toString();
    }

    /**
     * Listázza a pálya tartalmát 
     * @return pálya állapota printState -s formátumban
     */
    public String print(){
        StringBuilder res = new StringBuilder();
        res.append("Map:")
           .append("\n\troads: ");
        for (Road road : roads) {
            for (ILane lane : road.getLanes()) {
               res.append(lane.toList())
                  .append(", "); 
            }
        }
        res.append("\n\tintersections: ");
        for (Intersection intersection : intersections) {
            res.append(intersection.toList())
               .append(", ");
        }
        res.append("\n\tvehicles: ");
        for (IVehicle vehicle : vehicles) {
            res.append(vehicle.toList())
               .append(", ");
        }

        return res.toString();
    }
    /**
     * Részletes listázó
     * @return printState formátumú szöveg
     */
    public String printLong(){
        StringBuilder res = new StringBuilder();
        res.append(print());
        for (Road road : roads) {
            res.append(road.printLong());
        }
        for (IVehicle vehicle : vehicles) {
            res.append(vehicle.printLong());
        }
        return res.toString();
    }
    /**
     * Listázza a végpontokat kiválasztási célból
     * @return végpontok listája, sorszáma
     */
    public String printInterSections(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < intersections.size(); i++) {
            String[] tmp = intersections.get(i).toString().split("\n");
            res.append("(").append(i).append(") ")
               .append(tmp[1]).append(" ").append(tmp[2]);
        }
        return res.toString();
    }

    /**
     * Általános inicializálás a szimulációhoz.
     */
    public void initGeneral(){
        ArrayList<ILane> lanes = (ArrayList<ILane>)roads.get(0).getLanes();
        
        for (int i = 0; i < vehicles.size(); i++) {
            ILane lane = lanes.get(i);
            VehicleBase vehicle = (VehicleBase)vehicles.get(i);
            lane.enterVehicle(vehicle);
            vehicle.setLane(lane);
        }
    }


    /**
     * Jeges út szimulációjának inicializálása.
     */
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
