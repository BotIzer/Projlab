package main.java.models.interfaces;

import main.java.models.objects.vehicles.SnowPlower;

public interface ICommand {
   boolean start();
   boolean end();
   boolean saveState();
   boolean setRoute(IVehicle vehicle);
   boolean selectPlower(SnowPlower sp);
   boolean buyEquipment();
   boolean changeEquipment(ICleaning newEq);
   String printVehicles();
   String printInventory();
}