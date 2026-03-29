package main.java.models.objects;

import java.util.ArrayList;
import java.util.List;
import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.*;

/**
 * A felhasználót (játékost) és annak erőforrásait reprezentáló osztály.
 */
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

    /**
     * Felszerel egy kiválasztott tisztítófejet a megadott hókotróra.
     * @param sp a megadott hókotró
     * @param h a kivlasztott tisztítófej
     * @return felszerelés sikeressége
     */
    public boolean attach(SnowPlower sp, ICleaning h) {
        Console.print("->Player.attach(sp, h)");
        sp.attach(h);
        Console.print("<-Player.attach(sp, h): true");
        return true;
    }

    /**
     * Továbbítja a vásárlási szándékot a Shop osztály felé.
     * @param id az eszköz azonosítója
     * @param n a kivánt darabszám
     * @return művelet sikeressége
     */
    public boolean buyEquipment(int id , int n) {
        Console.print("->Player.buyEquipment(id, n)");
        Console.print("<-Player.buyEquipment(id, n): true");
        return true;
    }

    /**
     * Lecseréli a megadott hókotró aktuális felszerelését egy másikra.
     * @param sp a megadott hókotró
     * @return művelet sikeressége
     */
    public boolean changeEquipment(SnowPlower sp) {
        Console.print("->Player.changeEquipment(sp)");
        //TODO List heads and select one from inventory
        //sp.ChangeAttachment(null);
        Console.print("<-Player.changeEquipment(sp): true");
        return true;
    }

    /**
     * Kiírja a játékos leltárát.
     */
    public void printInventory(){
        Console.print("-----------Inventory---------");
        Console.print("Money:" + money);
        Console.print("Plowers: " + plowers.size());
        Console.print("Heads: " + heads.size());
    }

    /**
     * Frissíti a játékos eszköztárát az újonnan szerzett járművekkel és felszerelésekkel.
     * @param lsp a játékoshoz adandó hókotrók listája
     * @param lh a játékoshoz adandó tisztító fejek
     */
    public void updateInventory(List<SnowPlower> lsp, List<ICleaning> lh){
        Console.print("\t->Player.updateInventory()");
        Console.print("\t<-Player.updateInventory()");
    }

    /**
     * Levonja a megadott összeget a játékos egyenlegéből (money), amennyiben rendelkezésre áll a megfelelő fedezet.
     * @param n vásárlás költsége
     * @return művelet sikersége
     */
    public boolean removeFunds(int n){
        if (n <= money) {
           money -= n;
           return true; 
        } else {
            return false;
        }
    }
}
