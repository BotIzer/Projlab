package main.java.models.interfaces;
import main.java.models.objects.vehicles.SnowPlower;
public interface ICleaning {
    /**
     * Elvégzi a tisztítási folyamatot az adott sávon a hókotró erőforrásait használva.
     * @param lane A tisztítandó útsáv.
     * @param plow A tisztítást végző hókotró (erőforrás-kezeléshez).
     */
    void Clean(ILane lane, SnowPlower plow);
}