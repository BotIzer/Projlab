package main.java.models.objects;

import java.util.logging.Logger;

import main.java.models.interfaces.ICleaning;
import main.java.models.objects.vehicles.SnowPlower;

public class Shop {
    static Logger logger = Logger.getLogger(Shop.class.getName());

    public boolean processPurchase(SnowPlower buyer, ICleaning item) {
        logger.info("->Shop.ProcessPurchase(buyer, item)");
        logger.info("<-Shop.ProcessPurchase(buyer, item): true");
        return true;
    }
}
