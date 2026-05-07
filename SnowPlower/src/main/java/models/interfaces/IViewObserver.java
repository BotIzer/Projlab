package main.java.models.interfaces;

/**
 * Observer minta – megfigyelő oldal.
 * GUI elemek (MapPanel, InfoPanel) implementálják.
 */
public interface IViewObserver {
    void update(IObservable source);
}
