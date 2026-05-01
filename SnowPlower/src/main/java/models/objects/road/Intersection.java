package main.java.models.objects.road; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.FileHandler;


/**
 * A forgalmi csomópontokat kezeli, ahol az utak és a sávok találkoznak.
 */
public class Intersection {
    private int id;
    private List<Road> roads;

    //Betöltés fileból
    //szinkronizációs lista
    private List<Integer> pendingRoads = new ArrayList<>();
    //konstruktor file-ból
    public Intersection(Scanner sc){
       while (sc.hasNext(".*=.*")) {
            String line = sc.nextLine();
            String[] parts = line.split("=", 2);
            String key = parts[0].trim();
            String value = parts.length > 1 ? parts[1].trim() : "";

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "lanes" -> pendingRoads = FileHandler.parseList(value);
                default -> {break;} 
            }
        } 
    }
    //szinkronizáció
    public void resolve(Map<Integer, Road> roadsTmp){
        roads = pendingRoads.stream()
            .map(roadsTmp::get)
            .filter(Objects::nonNull)
            .map(Road.class::cast)
            .toList();
        pendingRoads.clear();
    }

    public Intersection(Road r){
        Console.print("\t!<<create>>Intersection");
        roads = new ArrayList<>();
        roads.add(r);
    }
    public void addRoad(Road r){
        roads.add(r);
    }
    public List<Road> getRoads(){ return roads; }

    public String toList(){
        return Integer.toString(id);
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("I");
        res.append("\nid=").append(id)
           .append("\nlanes=");
        for (Road road : roads) {
            for (ILane lane : road.getLanes()) {
                res.append(lane.toList()).append(";");
            }
        }
        return res.toString();
    }
}
