package main.java.models.objects.road; 
import java.util.ArrayList;
import java.util.List;

import main.java.models.objects.Console;

public class Intersection {
    private List<Road> roads;

    public Intersection(Road r){
        Console.print("\t!<<create>>Intersection");
        roads = new ArrayList<>();
        roads.add(r);
    }
    public void addRoad(Road r){
        roads.add(r);
    }
}
