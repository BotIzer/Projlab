package main.java.models.objects;

import java.util.List;
import java.util.logging.Logger;
import main.java.models.interfaces.*;

public class Player {
    static Logger logger = Logger.getLogger(Player.class.getName());

    private int money;
    private List<SnowPlow> plowers;
    private List<Bus> buses;
    private List<ICleaning> heads;

    public boolean attach(SnowPlow sp, ICleaning h) {
        logger.info("->Player.attach(SnowPlow sp, ICleaning h)");
        logger.info("<-Player.attach(SnowPlow sp, ICleaning h): true");
        return true;
    }

    public boolean buyEquipment(int id , int szam) {
        logger.info("->Player.buyEquipment(int id , int szam)");
        logger.info("<-Player.buyEquipment(int id , int szam): true");
        return true;
    }

    public boolean changeEquipment(SnowPlow sp) {
        logger.info("->Player.changeEquipment(SnowPlow sp)");
        logger.info("<-Player.changeEquipment(SnowPlow sp): true");
        return true;
    }
}
