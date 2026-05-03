package main.java.models.objects;


import java.util.ArrayList;
import java.util.List;

import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.SnowPlower;
import main.java.models.objects.vehicles.heads.BlowerHead;
import main.java.models.objects.vehicles.heads.DragonHead;
import main.java.models.objects.vehicles.heads.IceBreakerHead;
import main.java.models.objects.vehicles.heads.SalterHead;
import main.java.models.objects.vehicles.heads.GravelerHead;
import main.java.models.objects.vehicles.heads.SweeperHead;

/**
 * A játékon belüli áruházat reprezentálja, ahol a játékos új eszközöket és járműveket vásárolhat.
 */
public class Shop {

    private int cost = 0;

    public Shop(){
        Console.print("\t!<<create>>Shop");
    }

    private void listItems(){
        Console.print("""
        (0) SnowPlower - 500
        (1) Sweeper - 50
        (2) Blower - 100
        (3) Salter - 200               
        (4) IceBreaker - 200               
        (5) Dragon - 500
        (6) Graveler - 150
                """
        );
    }

    /**
     * Lebonyolítja a tranzakciót és visszaadja annak sikerességét.
     * @param p az adott játékos
     * @return tranzakció sikeressége
     */
    public boolean processPurchase(Player p) {
        cost = 0;
        Console.print("->Shop.ProcessPurchase(buyer, item)");
        listItems();
        String in = null;
        boolean res = true;
        int id;
        int n;
        try {
            in = Console.readLine();
            id = Integer.parseInt(in);
            Console.print("enter amount:");
            in = Console.readLine();
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
        }else  {
            Console.print("Insufficient funds");
            res = false;
        }
        String out = "<-Shop.ProcessPurchase(buyer, item): " + res;
        Console.print(out);
        return res;
    }
    
    /**
     * Létrehoz és visszaad egy adott típusú és mennyiségű hóeltakarító felszerelést tartalmazó listát,
     * valamint megnöveli az összesített költséget (cost) a vásárlás értékével.
     * @param id a kiválasztott eszköz azonosítója
     * @param n a vásárolni kivánt darabszám
     * @return az újonnan létrehozott eszközöket tartalmazó lista
     */
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
            case 6:
                for (int i = 0; i < n; i++) {
                   lh.add(new GravelerHead());
                   cost += 150;
                }
                break;
        
            default:
                return lh;
        }
        return lh;
    }
}
