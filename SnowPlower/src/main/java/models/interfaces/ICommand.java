package main.java.models.interfaces;


public interface ICommand {
   boolean start();
   boolean end();
   boolean saveState();
   boolean loadState();
   boolean setRoute();
   boolean selectVehicle();
   boolean buyEquipment();
   boolean changeEquipment();
   String printVehicles();
   String printInventory();
   void initGeneral();
   void initIcy();
   void loop();
}