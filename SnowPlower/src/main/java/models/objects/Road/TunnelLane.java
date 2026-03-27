package main.java.models.objects.road;

import main.java.models.objects.Console;

public class TunnelLane extends LaneBase{
   public TunnelLane(Intersection s, Intersection e){
    super(s,e);
    Console.print("\t!<<create>>TunnelLane");
   } 
}
