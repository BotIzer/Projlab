package main.java.models.objects;
import main.java.models.interfaces.ICommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.SnowPlower;

public class Console implements ICommand {
    private Player player;
    private Map map;
    private IVehicle selectedPlower;
    private FileHandler fileHandler;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void print(String msg){
        try {
            System.out.println(msg);
        } catch (Exception e) {
           e.printStackTrace(); 
        }
    }

    public void input(String cmd){
        print("-> Console.input()");
        print("<- Console.input()");
    }

    @Override
    public boolean start() {
        print("-> Console.start()");
        /*
        player = new Player();
        map = new Map();
         */
        fileHandler = new FileHandler();
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
        if (loc != null && !loc.equals("")) fileHandler.saveState(loc);
        else fileHandler.saveState("save.txt");
        String out = "<- Console.saveState():" + res; 
        print(out);
        return res;
    }

    @Override
    public boolean setRoute(IVehicle vehicle) {
        print("-> Console.setRoute(vehicle)");
        print("<- Console.setRoute(vehicle):true");
        return true;
    }

    @Override
    public boolean selectPlower(SnowPlower sp) {
        print("-> Console.selectPlower(sp)");
        print("<- Console.selectPlower(sp):true");
        return true;
    }

    @Override
    public boolean buyEquipment() {
        print("-> Console.buyEquipment()");
        print("<- Console.buyEquipment():true");
        return true;
    }

    @Override
    public boolean changeEquipment(ICleaning newEq) {
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


}
