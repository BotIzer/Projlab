package main.java.models.objects;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.SnowPlower;
import main.java.models.objects.vehicles.heads.BlowerHead;
import main.java.models.objects.vehicles.heads.DragonHead;
import main.java.models.objects.vehicles.heads.IceBreakerHead;
import main.java.models.objects.vehicles.heads.SalterHead;
import main.java.models.objects.vehicles.heads.SweeperHead;

public class Shop {

    private int cost = 0;

    public Shop(){
        Console.print("\t!<<create>>Shop");
    }
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private void listItems(){
        Console.print("""
        (0) SnowPlower - 500
        (1) Sweeper - 50
        (2) Blower - 100
        (3) Salter - 200               
        (4) IceBreaker - 200               
        (5) Dragon - 500
                """
        );
    }

    public boolean processPurchase(Player p) {
        cost = 0;
        Console.print("->Shop.ProcessPurchase(buyer, item)");
        listItems();
        String in = null;
        boolean res = true;
        int id;
        int n;
        try {
            in = br.readLine();
            id = Integer.parseInt(in);
            Console.print("enter amount:");
            in = br.readLine();
            n = Integer.parseInt(in);
        } catch (Exception e) {
            res = false;
            Console.print(e.getLocalizedMessage());
            return res;
        } 
        ArrayList<ICleaning> lh = (ArrayList<ICleaning>) handleInput(id, n);
        ArrayList<SnowPlower> lsp = new ArrayList<>();
        if (lh.isEmpty()) {
            for (int i = 0; i < n; i++) {
                cost += 500;
                lsp.add(new SnowPlower(50));
            }
        }
        if (p.removeFunds(cost)) {
            p.updateInventory(lsp, lh);
        }else Console.print("Insufficient funds");
        String out = "<-Shop.ProcessPurchase(buyer, item): " + res;
        Console.print(out);
        return res;
    }
    private List<ICleaning> handleInput(int id, int n){
        ArrayList<ICleaning> lh = new ArrayList<>();
        if(id == 0) return lh;
        switch (id) {
            case 1:
                for (int i = 0; i < n; i++) {
                   lh.add(new SweeperHead());
                   cost += 50;
                }
                break;

            case 2:
                for (int i = 0; i < n; i++) {
                   lh.add(new BlowerHead()); 
                   cost += 100;
                }
                break;
            case 3:
                for (int i = 0; i < n; i++) {
                   lh.add(new SalterHead()); 
                   cost += 200;
                }
                break;
            case 4:
                for (int i = 0; i < n; i++) {
                   lh.add(new IceBreakerHead()); 
                   cost += 200;
                }
                break;
            case 5:
                for (int i = 0; i < n; i++) {
                   lh.add(new DragonHead()); 
                   cost += 500;
                }
                break;
        
            default:
                return lh;
        }
        return lh;
    }
}
