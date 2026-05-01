package main.java.models.objects.vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.models.interfaces.ICleaning;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.Dijkstra;
import main.java.models.objects.FileHandler;
import main.java.models.objects.road.Intersection;

/**
 * A város normál forgalmát adó, önálló célokkal rendelkező jármű.
 */
public class Car extends VehicleBase
{

    public Car(double bs){
        super(bs);
        Console.print("\t!<<create>>Car");
    }

    @Override
    public void Move()
    {
         Console.print("-> Car.Move()");
         Console.print("<- Car.Move(): void");
    }

    @Override
    public void Stop()
    {
        Console.print("-> Car.Stop()");
        Console.print("<- Car.Stop(): void");
    }

    @Override
    public void Slipping()
    {
        Console.print("-> Car.Slipping()");
        lane.changeState("blocked");
        Console.print("<- Car.Slipping(): void");
    }

    /**
     * Útvonalat állít be Dijkstra-algoritmussal.
     * <ul>
     *   <li>1 elem  → jelenlegi sáv kezdőpontjától az adott kereszteződésig</li>
     *   <li>2 elem  → a két végpont közti legrövidebb út</li>
     *   <li>2+ elem → egymást követő szakaszok legrövidebb összekötése</li>
     * </ul>
     */
    @Override
    public void SetRoute(List<Intersection> intersections)
    {
        Console.print("-> Car.SetRoute(intersections)");

        if (gameMap == null || intersections == null || intersections.isEmpty()) {
            Console.print("<- Car.SetRoute(intersections): gameMap vagy útvonal hiányzik");
            return;
        }

        List<ILane> result = null;

        if (intersections.size() == 1) {
            // Jelenlegi sáv kezdőpontjától a megadott kereszteződésig
            Intersection startNode = (lane != null) ? lane.getStart() : null;
            if (startNode != null) {
                result = Dijkstra.dijkstra(gameMap, startNode, intersections.get(0));
            }
        } else if (intersections.size() == 2) {
            result = Dijkstra.dijkstra(gameMap, intersections.get(0), intersections.get(1));
        } else {
            // Egymást követő waypoint-ok közt legrövidebb utat fűzünk össze
            result = new ArrayList<>();
            for (int i = 0; i < intersections.size() - 1; i++) {
                List<ILane> segment = Dijkstra.dijkstra(
                    gameMap, intersections.get(i), intersections.get(i + 1));
                if (segment == null) { result = null; break; }
                result.addAll(segment);
            }
        }

        if (result != null) {
            route = new ArrayList<>(result);
            Console.print("<- Car.SetRoute(intersections): " + route.size() + " sáv beállítva");
        } else {
            Console.print("<- Car.SetRoute(intersections): nem található útvonal");
        }
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("V");
        res.append("\nid=" + id);
        res.append("\ntype=Car");
        res.append("\ncurrentPosition=" + currentPosition);
        res.append("\nlane=" + lane.toList());
        res.append("\nbaseSpeed=" + baseSpeed);
        res.append("\nroute=");
        for (ILane lane : route) {
            res.append(lane.toList())
               .append(";");
        }
        return res.toString();
    }
    //Fileból betöltés, szinkronizáció
    private Integer pendingLane;
    private List<Integer> pendingRoute = new ArrayList<>();
    @Override
    protected void applyData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
           switch (entry.getKey()) {
            case "id" -> id = Integer.parseInt(entry.getValue());
            case "currentPosition" -> currentPosition = Integer.parseInt(entry.getValue());
            case "lane" -> pendingLane = Integer.parseInt(entry.getValue());
            case "baseSpeed" -> baseSpeed = Integer.parseInt(entry.getValue());
            case "route" -> pendingRoute = FileHandler.parseList(entry.getValue());
            default -> {break;}
           } 
        }
    }

    @Override
    public void resolve(Map<Integer, ILane> lanes, Map<Integer, ICleaning> heads){
        if (pendingLane != null) lane = lanes.get(pendingLane);
        route = pendingRoute.stream()
            .map(lanes::get)
            .collect(Collectors.toCollection(ArrayList::new));
        pendingLane = null;
        pendingRoute.clear();
    }
}
