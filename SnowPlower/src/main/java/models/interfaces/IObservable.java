package main.java.models.interfaces;

/**
 * Observer minta – megfigyelt oldal.
 * Modell-elemek (LaneBase, VehicleBase, Player, Map) implementálják.
 */
public interface IObservable {
    void addObserver(IViewObserver o);
    void removeObserver(IViewObserver o);
    void notifyObservers();
}
