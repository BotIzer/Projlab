package main.java.models.objects.road;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.FileHandler;

/**
 * Egy konkrét útszakaszt reprezentál az úthálózatban.
 */
public class Road {
    private int id;
    private List<ILane> lanes;
    private double length;

    private static int idCtr = 0;

    public Road(List<ILane> ls, double len) {
        Console.print("\t!<<create>>Road");
        this.id = ++idCtr;
        lanes = ls;
        length = len;
    }

    public List<ILane> getLanes(){ return lanes; }

    public List<Intersection> initGeneral(){
        ArrayList<Intersection> intersections = new ArrayList<>();
        Intersection istart = new Intersection(this);
        Intersection iend = new Intersection(this);
        intersections.add(istart);
        intersections.add(iend);
        RoadLane rl = new RoadLane(istart, iend);
        BridgeLane bl = new BridgeLane(istart, iend);
        TunnelLane tl = new TunnelLane(istart, iend);
        this.lanes.add(rl);
        this.lanes.add(bl);
        this.lanes.add(tl);
        return intersections;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("R");
        res.append("\nid=" + id)
        .append("\nlanes=");
        for (ILane lane : lanes) {
           res.append(lane.toList())
           .append(";");
        }
        res.append("\nlength=")
           .append(length);
        return res.toString();
    }
    public String toList(){
        return Integer.toString(id);
    }
    public String printLong(){
        StringBuilder res = new StringBuilder();
        for (ILane lane : lanes) {
            res.append("\n")
               .append(lane.printLong(id));
        }
        return res.toString();
    }
    //Fileból betöltés
    private List<Integer> pendingLanes = new ArrayList<>();
    public static Road create(Scanner sc){
        Map<String, String> data = new HashMap<>();
        
        while (sc.hasNext(".*=.*")) {
            String[] parts = sc.nextLine().split("=", 2);
            data.put(parts[0].trim(), parts.length > 1 ? parts[1].trim() : "");
        }
        return new Road(data);
    }
    public Road(Map<String, String> data){
        for (Map.Entry<String, String> line : data.entrySet()) {
            switch (line.getKey()) {
                case "id" -> {
                    id = Integer.parseInt(line.getValue());
                    if (this.id > idCtr) idCtr = this.id;
                }
                case "lanes" -> pendingLanes = FileHandler.parseList(line.getValue());    
                case "length" -> length = Integer.parseInt(line.getValue());
                default -> {break;}
            }
        }
    }
    public void resolve(Map<Integer, ILane> lanesTmp){
        lanes = pendingLanes.stream()
                .map(lanesTmp::get)
                .filter(Objects::nonNull)    
                .collect(Collectors.toCollection(ArrayList::new));
        pendingLanes.clear();
    }
}
