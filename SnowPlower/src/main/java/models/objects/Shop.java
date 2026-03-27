package main.java.models.objects;

import java.util.logging.Logger;

public class Shop {
    static Logger logger = Logger.getLogger(Shop.class.getName());

    public boolean processPurchase(SnowPlow buyer, ICleaning item) {
        logger.info("->Shop.ProcessPurchase(SnowPlow buyer, ICleaning item)");
        logger.info("<-Shop.ProcessPurchase(SnowPlow buyer, ICleaning item): true");
        return true;
    }
}
