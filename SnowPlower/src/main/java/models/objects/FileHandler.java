package main.java.models.objects;

import java.util.logging.Logger;

/**
 * A játékállapot lemezre mentéséért és betöltéséért felelős segédosztály.
 */
public class FileHandler {

    public FileHandler(){
        Console.print("\t!<<create>>FileHandler");
    }

    /**
     * Elmenti a játék állapotát.
     * @param loc mentés helye
     * @return művelet sikeressége
     */
    public boolean saveState(String loc) {
        Console.print("\t->FileHandler.saveState(" + loc +")");
        Console.print("\t<-FileHandler.saveState(" + loc +"): true");
        return true;
    }

    /**
     * Betölti a játék állapotát.
     * @param loc mentés helye
     * @return művelet sikeressége
     */
    public boolean loadState(String loc) {
        Console.print("\t->FileHandler.loadState(" + loc +")");
        Console.print("\t<-FileHandler.loadState(" + loc +"): true");
        return true;
    }
}
