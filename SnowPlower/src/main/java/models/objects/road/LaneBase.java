package main.java.models.objects.road;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.java.models.interfaces.*;
import main.java.models.objects.Console;
import main.java.models.objects.FileHandler;

/**
 * Absztrakt alapsztályként elvégzi a specifikus sávok közös, mindennapi adminisztrációját.
 */
public abstract class LaneBase implements ILane {
    protected int id;
    protected Intersection start;
    protected Intersection end;
    protected List<IVehicle> vehicles;
    protected enum State{CLEAN, SNOWY, SNOWY_DEEP, ICY, BROKEN_ICE, BLOCKED, GRAVELED}
    State state;
    protected int carsPassedSinceSnow = 0;

    protected static int idCtr = 0;
    private static void syncId(int  lastId){
        if (lastId >= idCtr) {
            idCtr = lastId+1;
        }
    }
    public static void reset(){
        idCtr = 0;
    }

    protected LaneBase(Intersection s, Intersection e) {
        id = idCtr++;
        vehicles = new ArrayList<>();
        start = s;
        end = e;
    }

    @Override
    public boolean enterVehicle(IVehicle v) {
        Console.print("->LaneBase.enterVehicle(v)");
        vehicles.add(v);
        Console.print("<-LaneBase.enterVehicle(v)");
        return true;
    }

    @Override
    public boolean exitVehicle(IVehicle v) {
        Console.print("->LaneBase.exitVehicle(v)");
        vehicles.remove(v);
        if (this.state == State.SNOWY) {
            carsPassedSinceSnow++;
            if (carsPassedSinceSnow >= 10) {
                changeState("ICY");
            }
        }
        Console.print("<-LaneBase.exitVehicle(v)");
        return true;
    }

    @Override
    public Intersection getStart() { return start; }

    @Override
    public Intersection getEnd() { return end; }

    protected int blockedTimer = 0;
    private static final int BLOCKED_TIMEOUT = 30;

    @Override
    public boolean changeState(String ns) {
        try {
            State newState = State.valueOf(ns.toUpperCase());
            this.state = newState;
            if (newState == State.BLOCKED) {
                blockedTimer = BLOCKED_TIMEOUT;
            }
            if (newState == State.CLEAN) {
                carsPassedSinceSnow = 0;
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String getState() {
        return state != null ? state.toString() : "CLEAN";
    }

    @Override
    public List<IVehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    /** TC24: 30 tick után BLOCKED → ICY visszaáll */
    @Override
    public void tickBlocked() {
        if (state == State.BLOCKED) {
            blockedTimer--;
            if (blockedTimer <= 0) {
                changeState("ICY");
            }
        }
    }

    @Override
    public boolean clear() {
        Console.print("->LaneBase.clear()");
        Console.print("<-LaneBase.clear()");
        carsPassedSinceSnow = 0;
        return true;
    }
    @Override
    public String toList() {
        return Integer.toString(id);
    }
    /**
     * Részletes listázó segédfüggvény 
     * @param roadId road idje
     * @return printState formátumú szöveg
     */
    @Override
    public String printLong(int roadId) {
        StringBuilder res = new StringBuilder("Lane");
        res.append(id)
           .append(": ")
           .append(roadId)
           .append(", ")
           .append(start.toList())
           .append("-")
           .append(end.toList())
           .append(", ")
           .append(state.toString());
        for (IVehicle vehicle : vehicles) {
            res.append("\n\t")
                .append(vehicle.toList());
        }
        return res.toString();
    }
    
    //Fileból betöltés, szinkronizáció

    protected Integer pendingStart;
    protected Integer pendingEnd;
    protected List<Integer> pendingVehicles = new ArrayList<>();

    public static ILane create(Scanner sc){

        Map<String, String> data = new HashMap<>();
        
        while (sc.hasNext(".*=.*")) {
            String[] parts = sc.nextLine().split("=", 2);
            data.put(parts[0].trim(), parts.length > 1 ? parts[1].trim() : "");
        }
        String type = data.getOrDefault("type", "");
        switch (type) {
            case "TunnelLane" -> {return new TunnelLane(data);}
            case "RoadLane" -> {return new RoadLane(data);}
            case "BridgeLane" -> {return new BridgeLane(data);}
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    protected LaneBase(Map<String, String> data){
        for (Map.Entry<String, String> line : data.entrySet()) {
           switch (line.getKey()) {
            case "id" -> {
                id = Integer.parseInt(line.getValue());
                syncId(id);
            }
            case "start" -> pendingStart = Integer.parseInt(line.getValue());
            case "end" -> pendingEnd = Integer.parseInt(line.getValue());
            case "vehicles" -> pendingVehicles = FileHandler.parseList(line.getValue());
            case "state" -> state = State.valueOf(line.getValue().toUpperCase()); 
            default -> {break;}
           } 
        }
    }
    public void resolve(Map<Integer, Intersection> intersections, Map<Integer, IVehicle> vehiclesTmp){
        if (pendingStart != null) start = intersections.get(pendingStart);
        if (pendingEnd != null) end = intersections.get(pendingEnd); 
        vehicles = pendingVehicles.stream()
            .map(vehiclesTmp::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));
        pendingStart = null;
        pendingEnd = null;
        pendingVehicles.clear();
    }
}
