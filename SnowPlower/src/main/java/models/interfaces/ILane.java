package main.java.models.interfaces;

public interface ILane {
    public boolean enterVehicle(IVehicle v);
    public boolean exitVehicle(IVehicle v);
    public boolean clear();
    public boolean changeState(String ns);
}
