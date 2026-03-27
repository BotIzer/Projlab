package main.java.models.objects;

import java.util.logging.Logger;

public class FileHandler {

    public FileHandler(){
        Console.print("\t!<<create>>FileHandler");
    }

    public boolean saveState(String loc) {
        Console.print("\t->FileHandler.saveState(" + loc +")");
        Console.print("\t<-FileHandler.saveState(" + loc +"): true");
        return true;
    }

    public boolean loadState(String loc) {
        Console.print("\t->FileHandler.loadState(" + loc +")");
        Console.print("\t<-FileHandler.loadState(" + loc +"): true");
        return true;
    }
}
