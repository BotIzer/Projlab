package main.java.models.interfaces;

import java.util.List;

/**
 * A rendszer irányításához szükséges magas szintű parancsok interfésze.
 */
public interface ICommand {
   /**
    * Elindítja a játékot.
    * @return művelet sikeressége
    */
   boolean start();

   /**
    * Befejezi a játékot, rákérdez mentési szándékra,
    * @param args -s <file> kérdés nélkül menti a megadott file-ba
    * @return művelet sikeressége
    */
   boolean end(List<String> args);

   /**
    * Elmenti a játék aktuális állását.
    * @return művelet sikeressége
    */
   boolean saveState(String loc);

   /**
    * Betölti a játék elmentett állását.
    * @return művelet sikeressége
    */
   boolean loadState(String loc);

   /**
    * Kijelöli az utvonalat.
    * @return művelet sikeressége
    */
   boolean setRoute();

   /**
    *  Kiválaszt egy járművet.
    * @return művelet sikeressége
    */
   boolean selectVehicle();

   /**
    * Kezdeményezi egy új felszerelés vásárlását.
    * @return művelet sikeressége
    */
   boolean buyEquipment();

   /**
    * Lecseréli a tisztítófejet.
    * @return művelet sikeressége
    */
   boolean changeEquipment();

   /**
    * Lekérdezi a járművek listáját.
    * @return művelet sikeressége
    */
   String printVehicles();

   /**
    * Lekérdezi a játékos felszerelését.
    * @return művelet sikeressége
    */
   String printInventory();

    /**
     * Általános inicializálás a szimulációhoz.
     */
   void initGeneral();

    /**
     * Jeges út szimulációjának inicializálása.
     */
   void initIcy();

    /**
     * Végrehajt egy szimulációs ciklust a térképen.
     */
   void loop();
    /**
     * Bezárja az olvasót a program leállásakor
     */
   void closeReader();
}