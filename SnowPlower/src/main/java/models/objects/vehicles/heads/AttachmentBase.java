package main.java.models.objects.vehicles.heads;
import main.java.models.objects.vehicles.SnowPlower;
import main.java.models.interfaces.*;
public abstract class AttachmentBase implements ICleaning {
    protected double price;
    /**
     * Absztrakt metódus, amely a konkrét takarítási folyamatot vezérli[cite: 191].
     * A konkrét leszármazottakban (pl. DragonHead, SalterHead) kell kifejteni[cite: 191].
     * * @param lane Az adott útsáv, amit tisztítani kell[cite: 191].
     * @param plow A hókotró példány, amelynek az erőforrásait (só, kerozin) használja a fej[cite: 191].
     */
    @Override
    public abstract void Clean(ILane lane, SnowPlower plow);

}