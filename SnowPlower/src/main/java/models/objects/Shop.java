package main.java.models.objects;


import main.java.models.interfaces.ICleaning;
import main.java.models.objects.vehicles.SnowPlower;

public class Shop {

    public Shop(){
        Console.print("\t!<<create>>Shop");
    }

    public boolean processPurchase(SnowPlower buyer, ICleaning item) {
        Console.print("\t->Shop.ProcessPurchase(buyer, item)");
        Console.print("\t<-Shop.ProcessPurchase(buyer, item): true");
        return true;
    }
}
