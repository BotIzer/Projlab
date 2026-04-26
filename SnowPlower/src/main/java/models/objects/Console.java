package main.java.models.objects;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;
import main.java.models.objects.vehicles.Bus;
import main.java.models.objects.vehicles.Car;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * A felhasználói interakciókért és a parancsok feldolgozásáért felelős központi vezérlő osztály.
 */
public class Console implements ICommand {
    private Player player;
    private Map map;
    private IVehicle selectedVehicle;
    private FileHandler fileHandler;
    private Shop shop;
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void print(String msg){
        try {
            System.out.println(msg);
        } catch (Exception e) {
           e.printStackTrace(); 
        }
    }
    public static String readLine(){
        String res = null;
        try {
            res = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void input(String cmd){
        print("-> Console.input()");
        print("<- Console.input()");
    }

    @Override
    public boolean start() {
        print("-> Console.start()");
        player = new Player();
        map = new Map();
        fileHandler = new FileHandler();
        shop = new Shop();
        print("<- Console.start():true");
        return true;
    }

    @Override
    public boolean end() {
        print("-> Console.end()");
        print("<- Console.end():true");
        return true;
    }

    @Override
    public boolean saveState() {
        print("-> Console.saveState()");
        String loc = null;
        boolean res = true;
        print("Save to: (default: save.txt)");
        try {
            loc = br.readLine();
        } catch (Exception e) {
            res = false;
            print(e.getMessage());
        }
        if (loc != null && !loc.equals("")) fileHandler.saveState(loc, player, map);
        else fileHandler.saveState("save.txt", player, map);
        String out = "<- Console.saveState():" + res; 
        print(out);
        return res;
    }
    @Override
    public boolean loadState() {
        print("-> Console.loadState()");
        boolean res = true;
        String loc = null;
        try {
            print("Enter file to load from: (default: save.txt)");
            loc = br.readLine();  
        } catch (Exception e) {
            res = false;
            print(e.getMessage()); 
        }
        if (loc != null && !loc.equals("")) fileHandler.loadState(loc);
        else fileHandler.loadState("save.txt");
        String out = "<- Console.loadState():" + res; 
        print(out);
        return res;
    }

    @Override
    public boolean setRoute() {
        print("-> Console.setRoute(vehicle)");
        selectVehicle();
        selectedVehicle.SetRoute(null, null);
        print("<- Console.setRoute(vehicle):true");
        return true;
    }

    @Override
    public boolean selectVehicle() {
        print("-> Console.selectVehicle()");
        String id = null;
        print("Select a vehicle:");
        map.listVehicles();
        try {
            id = br.readLine();
        } catch (Exception e) {
            print(e.getMessage());
        }
        selectedVehicle = map.getVehicles().get(Integer.parseInt(id));
        print("<- Console.selectVehicle():true");
        return true;
    }

    @Override
    public boolean buyEquipment() {
        print("-> Console.buyEquipment()");
        shop.processPurchase(player);
        print("<- Console.buyEquipment():true");
        return true;
    }

    @Override
    public boolean changeEquipment() {
        print("-> Console.changeEquipment(newEq)");
        print("<- Console.changeEquipment(newEq):true");
        return true;
    }

    @Override
    public String printVehicles() {
        print("-> Console.printVehicles()");
        print("<- Console.printVehicles():String");
        return "";
    }

    @Override
    public String printInventory() {
        print("-> Console.printInventory()");
        print("<- Console.printInventory():String");
        return "";
    }
    @Override
    public void initGeneral(){
        Console.print("----------------Initialization--------------");
        ArrayList<ILane> lanes = new ArrayList<>();
        Road r = new Road(lanes, 5);
        ArrayList<Intersection> intersections = (ArrayList<Intersection>)r.initGeneral();
        map.addRoad(r);
        for (Intersection intersection : intersections) {
            map.addIntersections(intersection);
        }
        SnowPlower sp = new SnowPlower(45.0);
        Car c = new Car(50.0);
        ArrayList<Road> route = new ArrayList<>();
        Bus b = new Bus(40.0, "1", route);  
        map.addVehicle(sp);
        map.addVehicle(c);
        map.addVehicle(b);
        map.initGeneral(); 
        Console.print("------------End of Initialization-----------");
    }
    @Override
    public void initIcy(){
        Console.print("----------------Initialization--------------");
        ArrayList<ILane> lanes = new ArrayList<>();
        Road r = new Road(lanes, 5);
        ArrayList<Intersection> intersections = (ArrayList<Intersection>)r.initGeneral();
        map.addRoad(r);
        for (Intersection intersection : intersections) {
            map.addIntersections(intersection);
        }
        SnowPlower sp = new SnowPlower(45.0);
        Car c1 = new Car(50.0);
        Car c2 = new Car(30.0);
        ArrayList<Road> route = new ArrayList<>();
        Bus b = new Bus(40.0, "1", route);  
        map.addVehicle(sp);
        map.addVehicle(c1);
        map.addVehicle(c2);
        c1.SetRoute(null, null);
        c2.SetRoute(null, null);
        map.addVehicle(b);
        map.initIcy(); 
        Console.print("------------End of Initialization-----------");
    }

    @Override
    public void loop(){
        map.loop();
    }


}
