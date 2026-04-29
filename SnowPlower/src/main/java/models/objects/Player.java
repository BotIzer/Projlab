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
     * @param id a kivlasztott tisztítófej sorszáma
     * @return felszerelés sikeressége
     */
    public boolean attach(SnowPlower sp, int id) {
        Console.print("->Player.attach(sp, h)");
        ICleaning head = heads.get(id);
        heads.remove(id);
        sp.attach(head);
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
        sp.listHeads();
        String id = Console.readLine();
        sp.ChangeAttachment(Integer.parseInt(id));
        Console.print("<-Player.changeEquipment(sp): true");
        return true;
    }

    /**
     * Kiírja a játékos leltárát.
     * @return szöveg, formátum:
     * Player: 
        balance: [jelenlegi pénzmennyiség] 
        plowers: [id1], [id2], [id3],…[idn] 
        buses: [id1], [id2], [id3],…[idn] 
        heads: Sweeper [n1], Blower [n2], Salter[n3], IceBreaker[n4], Graveler[n5], Dragon[n6]
     */
    public String printInventory(){
        StringBuilder res = new StringBuilder();
        res.append("Player:")
           .append("\n\tbalance: ")
           .append(money)
           .append("\n\tplowers: ");
        for (SnowPlower plower : plowers) {
            res.append(plower.toList())
               .append(", ");
        }
        res.append("\n\tbuses:");
        for (Bus bus : buses) {
            res.append(bus.toList())
               .append(", ");
        }
        int sweeper = 0; 
        int blower = 0;
        int salter = 0;
        int iceBreaker = 0; 
        int graveler = 0;
        int dragon = 0;
        for (ICleaning head : heads) {
            String type = head.print();
            switch (type) {
                    case "SweeperHead" -> sweeper++;
                    case "BlowerHead" -> blower++;
                    case "SalterHead" -> salter++;
                    case "IceBreakerHead" -> iceBreaker++;
                    case "GravelerHead" -> graveler++;
                    case "DragonHead" -> dragon++;
                    default -> {break;}
            }
        }

        res.append("\n\theads: Sweeper ") .append(sweeper)
       .append(", Blower ").append(blower)
       .append(", Salter ").append(salter)
       .append(", IceBreaker ").append(iceBreaker)
       .append(", Graveler ").append(graveler)
       .append(", Dragon ").append(dragon);

        return res.toString();
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
    public String listHeads(){
        StringBuilder list = new StringBuilder();
        for (ICleaning head : heads) {
            list.append("\n(");
            list.append(heads.indexOf(head));
            list.append(") ");
            list.append(head.print());
        }
        return list.toString();
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder("P");
        res.append("\nbalance=" + money);
        res.append("\nplowers=");
        for (SnowPlower plower : plowers) {
            res.append(plower.toList()); 
            res.append(";");
        }
        res.append("\nbuses=");
        for (Bus bus : buses) {
            res.append(bus.toList()); 
            res.append(";");
        }
        res.append("\nheads=");
        StringBuilder headString = new StringBuilder();
        for (ICleaning head : heads) {
            res.append(head.toList());
            headString.append(head.toString());
        }
        res.append(headString);
        return res.toString();
    }
}
