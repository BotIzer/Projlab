package main.java.models.objects;


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
     * @param player játékos állapota
     * @param map pálya állapota
     * @return művelet sikeressége
     */
    public boolean saveState(String loc, Player player, Map map) {
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
    /**
     * Létrehozza a mentési formátumnak megfelelő szöveget
     * @param p a játékos állapota
     * @param map a játék állapota
     * @return megformázott szöveg
     */
    public String format(Player p, Map map){
        StringBuilder res = new StringBuilder();
        res.append(p.toString());
        res.append(map.toString());

        return res.toString();
    }
}
