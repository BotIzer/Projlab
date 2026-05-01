package main.java.models.objects.vehicles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import main.java.models.interfaces.*;
import main.java.models.objects.Console;
import main.java.models.objects.road.Intersection;

/**
 * Minden jármű absztrakt alaposztálya, amely definiálja az alapvető mozgási képességeket és a környezettel való kapcsolatot.
 */
public abstract class VehicleBase implements IVehicle {
    protected int id; //privat id
    protected double currentPosition = 0.0; //A jarmu jelenlegi pozicioja
    protected ILane lane; //Az a ut, amin a jarmu tartozkodik
    protected double baseSpeed; //A jarmu sebessege
    protected List<ILane> route; //A jarmu kijelolt utja, amit meg fog tenni

    protected boolean crashed = false; //Egy olyan boolean tagvaltozo mely azt figyeli, hogy a kocsi karambolozott e
    protected int crashTimer = 0; //Egy idozito, amely a kotelezo megallast figyeli

    //Ez a metodus beallitja az osztaly lane tagvaltozojat
    public void setLane(ILane l)
    {
        this.lane = l;
    }

    //Ez a metodus beallitja az osztaly currentPosition tagvaltozojat
    public double getPosition()
    {
        return this.currentPosition;
    }

    //utkozest megvalosito fuggveny
    public void Collide(IVehicle vehicle)
    {
        this.Stop(); //megallitja a jarmuvet
        this.crashed = true;
        this.crashTimer = 30;
        if(this.lane != null)
            this.lane.changeState("BLOCKED"); //az ut allapotat blokkoltra rakja
    }

    //jarmu konstruktora
    protected VehicleBase(double bs){
        currentPosition = 0.0;
        baseSpeed = bs;
    }

    //A jarmu mozgasat megvalostito fuggveny
    @Override
    public void Move() {
        //Azt figyeli, hogy a kotelezo ido lejart e mar
        if (crashed) {
            if (crashTimer > 0) {
                crashTimer--;
            } else {
                crashed = false;
            }
            return; 
        }

        //Ha a kocsi sebessege nulla akkor visszater, mert nem tud menni
        if (baseSpeed == 0.0) return;

        currentPosition += baseSpeed;

        // Amikor a jármű a sáv végére ér
        if (currentPosition >= 100.0) {
            if (route != null && !route.isEmpty() && this.lane != null) {
                
                // Megkeressük a jelenlegi sávunk indexét az útvonalban
                int currentIndex = route.indexOf(this.lane);
                
                if (currentIndex != -1 && currentIndex < route.size() - 1) {
                    // Ha van következő sáv, rálépünk
                    ILane nextLane = route.get(currentIndex + 1);
                    
                    lane.exitVehicle(this);
                    boolean canEnter = nextLane.enterVehicle(this);
                    
                    if (canEnter) {
                        setLane(nextLane);
                        currentPosition = 0.0;
                    } else {
                        // Ha nem lehet belépni (pl. Blocked), a jármű megáll
                        this.Stop();
                        lane.exitVehicle(this);
                    }
                } else {
                    // Elértük a célállomást (az útvonal végére értünk)
                    this.Stop();
                    lane.exitVehicle(this);
                }
            }
        }
    }
    
    //A kocsi megallasat megvalosito fuggveny
    @Override
    public void Stop()
    {
        this.baseSpeed = 0.0;
    }

    //A kocsi megcsuszasat megvalosito fuggveny
    @Override
    public void Slipping()
    {
        //A kocsi megcsuszasara jegen 5% az esely
        Random rand = new Random();
        int randomChance = rand.nextInt(100) + 1;
        boolean collisionHappened = false;

        if (randomChance <= 5) {
            IVehicle targetVehicle = null;
            double minDistance = Double.MAX_VALUE;

            if (this.lane != null && this.lane.getVehicles() != null) {
                for (IVehicle v : this.lane.getVehicles()) {
                    if (v != this && ((VehicleBase)v).getPosition() > this.currentPosition) {
                        double distance = ((VehicleBase)v).getPosition() - this.currentPosition;
                        if (distance < minDistance) {
                            minDistance = distance;
                            targetVehicle = v;
                        }
                    }
                }
            }

            if (targetVehicle != null) {
                targetVehicle.Collide(this);
                this.Collide(targetVehicle);
            }
        }
    }

    //A jarmu utjat beallitja 
    @Override
    public void SetRoute(List<ILane> validRoute)
    {
       this.route = validRoute;
    }

    @Override
    public String toList(){
        return Integer.toString(id);
    }
    @Override
    public String printLong() {
        StringBuilder res = new StringBuilder(this.getClass().getSimpleName());
        res.append(id)
           .append(": ")
           .append(id).append(", ")
           .append(lane.toList()).append(", ")
           .append("\nRoute: ");
        for (ILane node : route) {
            res.append(node.toList())
               .append(", ");
        }
        return res.toString();
    }
    //Fileból betöltés, szinkronizálás
    protected abstract void applyData(Map<String, String> data);
    public abstract void resolve(Map<Integer, ILane> lanes, Map<Integer, ICleaning> headsTmp);
    public static IVehicle create(Scanner sc){
        Map<String, String> data = new HashMap<>();
        
        while (sc.hasNext(".*=.*")) {
            String[] parts = sc.nextLine().split("=", 2);
            data.put(parts[0].trim(), parts.length > 1 ? parts[1].trim() : "");
        }

        String type = data.getOrDefault("type", "");

        IVehicle v = switch (type) {
            case "SnowPlower" -> new SnowPlower(30);
            case "Bus" -> new Bus(30, "", new ArrayList<>());
            case "Car" -> new Car(30);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };

        ((VehicleBase)v).applyData(data); 

        return v;
    }
}
