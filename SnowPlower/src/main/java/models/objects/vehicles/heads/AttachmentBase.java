package main.java.models.objects.vehicles.heads;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;
import main.java.models.interfaces.*;

/**
 * Ez az absztrakt osztály szolgál alapul minden hóeltakarító eszköz (fej) számára.
 */
public abstract class AttachmentBase implements ICleaning {
    protected int id;
    protected double price;
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

}