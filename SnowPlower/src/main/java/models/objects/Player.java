package main.java.models.objects;

import java.util.List;
import java.util.logging.Logger;
import main.java.models.interfaces.*;
import main.java.models.objects.vehicles.*;

public class Player {
    static Logger logger = Logger.getLogger(Player.class.getName());

    private int money;
    private List<SnowPlower> plowers;
    private List<Bus> buses;
    private List<ICleaning> heads;

    public boolean attach(SnowPlower sp, ICleaning h) {
        logger.info("->Player.attach(sp, h)");
        logger.info("<-Player.attach(sp, h): true");
        return true;
    }

    public boolean buyEquipment(int id , int n) {
        logger.info("->Player.buyEquipment(id, n)");
        logger.info("<-Player.buyEquipment(id, n): true");
        return true;
    }

    public boolean changeEquipment(SnowPlower sp) {
        logger.info("->Player.changeEquipment(sp)");
        logger.info("<-Player.changeEquipment(sp): true");
        return true;
    }
}
