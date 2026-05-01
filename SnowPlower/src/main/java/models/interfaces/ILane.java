package main.java.models.interfaces;

import main.java.models.objects.road.Intersection;

/**
 * Biztosítja, hogy minden sávtípus egységesen kezelhető legyen.
 */
public interface ILane {

    /** @return a sáv kezdő kereszteződése */
    Intersection getStart();

    /** @return a sáv záró kereszteződése */
    Intersection getEnd();

    /**
     * Egy jármű (IVehicle) sávba történő belépését kezeli, és visszaadja, hogy a belépés engedélyezett és sikeres volt-e.
     * @param v adott jármű
     * @return művelet sikeressége
     */
    public boolean enterVehicle(IVehicle v);

    /**
     * Egy adott jármű sávból való kilépését regisztrálja a rendszerben, sikerességét logikai értékkel jelzi.
     * @param v adott jármű
     * @return művelet sikeressége
     */
    public boolean exitVehicle(IVehicle v);

    /**
     * A sáv kiürítését vagy takarítását kezdeményezi (minden implementáló osztály a saját módján).
     * @return művelet sikeressége
     */
    public boolean clear();

    /**
     * A sáv forgalmi állapotának (például nyitott, lezárt, karbantartás alatt) megváltoztatásáért felel.
     * @param ns forgalmi állapot
     * @return művelet sikeressége
     */
    public boolean changeState(String ns);
    public String toList();
    public String printLong(int roadId);
}
