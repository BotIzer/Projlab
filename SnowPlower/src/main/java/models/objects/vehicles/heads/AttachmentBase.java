package main.java.models.objects.vehicles.heads;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import main.java.models.interfaces.*;

/**
 * Ez az absztrakt osztály szolgál alapul minden hóeltakarító eszköz (fej) számára.
 */
public abstract class AttachmentBase implements ICleaning {
    protected int id;
    protected double price;

    private static int idCtr = 0;
    protected AttachmentBase(){
       this.id = ++idCtr;
    }
    /**
     * Absztrakt metódus, amely a konkrét takarítási folyamatot vezérli[cite: 191].
     * A konkrét leszármazottakban (pl. DragonHead, SalterHead) kell kifejteni[cite: 191].
     * * @param lane Az adott útsáv, amit tisztítani kell[cite: 191].
     * @param plow A hókotró példány, amelynek az erőforrásait (só, kerozin) használja a fej[cite: 191].
     */
    @Override
    public void Clean(ILane lane, SnowPlower plow) {
        Console.print("\t\t\t\t-> AttachmentBase.Clean(lane, plower)");
        lane.clear();
        Console.print("\t\t\t\t<- AttachmentBase.Clean(lane, plower)");
    }
    @Override
    public String toList() {
        return Integer.toString(id);
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append( "H");
        res.append("\nid=");
        res.append(id);
        res.append("\ntype=");
        res.append(print());
        return res.toString();
    }

    //Fileból betöltés
    
    protected AttachmentBase(Map<String, String> data){
        for (Entry<String, String> line : data.entrySet()) {
            if (line.getKey().equals("id")) {
                id = Integer.parseInt(line.getValue());
                if (this.id > idCtr) idCtr = this.id;
            }
        }
    }
    public static ICleaning create(Scanner sc){

        Map<String, String> data = new HashMap<>();
        
        while (sc.hasNext(".*=.*")) {
            String[] parts = sc.nextLine().split("=", 2);
            data.put(parts[0].trim(), parts.length > 1 ? parts[1].trim() : "");
        }

        String type = data.getOrDefault("type", "");

        switch (type) {
            case "SweeperHead" -> {return new SweeperHead(data);}
            case "BlowerHead" -> {return new BlowerHead(data);}
            case "IceBreakerHead" -> {return new IceBreakerHead(data);}
            case "SalterHead" -> {return new SalterHead(data);}
            case "DragonHead" -> {return new DragonHead(data);}
            //TODO uncomment when added
            //case "GravelerHead" -> {return new GravelerHead(data);}
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

}