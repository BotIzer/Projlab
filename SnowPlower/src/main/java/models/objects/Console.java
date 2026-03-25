package main.java.models.objects;
import main.java.models.interfaces.ICommand;
import java.util.logging.Logger;
import main.java.models.interfaces.*;
import main.java.models.objects.*;

public class Console implements ICommand {
    static Logger logger = Logger.getLogger(Console.class.getName());
    private Player player;
    private Map map;
    private IVehicle selectedPlower;

    public void input(String cmd){
        logger.info("-> Console.input()");
        logger.info("<- Console.input()");
    }

    @Override
    public boolean start() {
        logger.info("-> Console.start()");
        logger.info("<- Console.start():true");
        return true;
    }

    @Override
    public boolean end() {
        logger.info("-> Console.end()");
        logger.info("<- Console.end():true");
        return true;
    }

    @Override
    public boolean saveState() {
        logger.info("-> Console.saveState()");
        logger.info("<- Console.saveState():true");
        return true;
    }

    @Override
    public boolean setRoute(IVehicle vehicle) {
        logger.info("-> Console.setRoute(vehicle)");
        logger.info("<- Console.setRoute(vehicle):true");
        return true;
    }

    @Override
    public boolean selectPlower(SnowPlower sp) {
        logger.info("-> Console.selectPlower(sp)");
        logger.info("<- Console.selectPlower(sp):true");
        return true;
    }

    @Override
    public boolean buyEquipment() {
        logger.info("-> Console.buyEquipment()");
        logger.info("<- Console.buyEquipment():true");
        return true;
    }

    @Override
    public boolean changeEquipment(ICleaning newEq) {
        logger.info("-> Console.changeEquipment(newEq)");
        logger.info("<- Console.changeEquipment(newEq):true");
        return true;
    }

    @Override
    public String printVehicles() {
        logger.info("-> Console.printVehicles()");
        logger.info("<- Console.printVehicles():String");
        return "";
    }

    @Override
    public String printInventory() {
        logger.info("-> Console.printInventory()");
        logger.info("<- Console.printInventory():String");
        return "";
    }


}
