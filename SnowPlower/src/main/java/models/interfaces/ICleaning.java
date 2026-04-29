package main.java.models.interfaces;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Ez az interfész definiálja a hókotrókra szerelhető különféle tisztítófejek kötelező, közös viselkedését.
 */
public interface ICleaning {

    /**
     * Elvégzi a tisztítási folyamatot az adott sávon a hókotró erőforrásait használva.
     * @param lane A tisztítandó útsáv.
     * @param plow A tisztítást végző hókotró (erőforrás-kezeléshez).
     */
    public void Clean(ILane lane, SnowPlower plow);
    /**
     * Mentési segédfüggvény
     * @return fej id-je
     */
    public String toList();
    /**
     * Listázási segédfüggvény
     * @return Tipus/Név
     */
    public String print();
}