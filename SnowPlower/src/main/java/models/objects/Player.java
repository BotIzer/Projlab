package main.java.models.objects;

import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.*;

public class Player {

    private int money;
    private List<SnowPlower> plowers;
    private List<Bus> buses;
    private List<ICleaning> heads;

    public Player(){
        money = 500;
        plowers = new ArrayList<>();
        buses = new ArrayList<>();
        heads = new ArrayList<>();
        Console.print("\t!<<create>>Player");
    }

    public boolean attach(SnowPlower sp, ICleaning h) {
        Console.print("->Player.attach(sp, h)");
        sp.attach(h);
        Console.print("<-Player.attach(sp, h): true");
        return true;
    }

    public boolean buyEquipment(int id , int n) {
        Console.print("->Player.buyEquipment(id, n)");
        Console.print("<-Player.buyEquipment(id, n): true");
        return true;
    }

    public boolean changeEquipment(SnowPlower sp) {
        Console.print("->Player.changeEquipment(sp)");
        //TODO List heads and select one from inventory
        //sp.ChangeAttachment(null);
        Console.print("<-Player.changeEquipment(sp): true");
        return true;
    }
    public void printInventory(){
        Console.print("-----------Inventory---------");
        Console.print("Money:" + money);
        Console.print("Plowers: " + plowers.size());
        Console.print("Heads: " + heads.size());
    }
    public void updateInventory(List<SnowPlower> lsp, List<ICleaning> lh){
        Console.print("\t->Player.updateInventory()");
        Console.print("\t<-Player.updateInventory()");
    }
    public boolean removeFunds(int n){
        if (n <= money) {
           money -= n;
           return true; 
        } else {
            return false;
        }
    }
}
