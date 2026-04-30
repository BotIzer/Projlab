package main.java.models.objects.vehicles.heads;
import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Ez az absztrakt osztály szolgál alapul minden hóeltakarító eszköz (fej) számára.
 */
public abstract class AttachmentBase implements ICleaning {
    protected int id;
    protected double price;
    public AttachmentBase(int id, int price) {
        this.id = id;
        this.price = price;
    }
    /**
     * Absztrakt metódus, amely a konkrét takarítási folyamatot vezérli[cite: 191].
     * A konkrét leszármazottakban (pl. DragonHead, SalterHead) kell kifejteni[cite: 191].
     * * @param lane Az adott útsáv, amit tisztítani kell[cite: 191].
     * @param plow A hókotró példány, amelynek az erőforrásait (só, kerozin) használja a fej[cite: 191].
     */
    @Override
    public abstract void Clean(ILane lane, SnowPlower plow); //{
        //Console.print("\t\t\t\t-> AttachmentBase.Clean(lane, plower)");
        //lane.clear();
        //Console.print("\t\t\t\t<- AttachmentBase.Clean(lane, plower)");
    //}

    /**kiírásnál: Az objektum magát alakítja listaelem-reprezentációvá, 
     * ahelyett, hogy egy másik osztály kérdezné le az ID-ját. **/
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
    public String getHead() {
        return this.toString();
    }
}