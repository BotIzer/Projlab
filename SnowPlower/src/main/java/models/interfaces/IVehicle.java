package main.java.models.interfaces;
import java.util.List;

import main.java.models.objects.road.Intersection;

/**
 * Minden a játékban szereplő közlekedési eszköz (busz, autó, hókotró) közös interfésze.
 */
public interface IVehicle {
    /**
     * Előírja a jármű normál haladási logikájának megvalósítását.
     */
    public void Move();

    /**
     * Előírja a jármű megállási logikájának megvalósítását.
     */
    public void Stop();

    /**
     * Előírja a megcsúszás (tapadásvesztés a jégen) viselkedésének megvalósítását.
     */
    public void Slipping();

    /**
     * Előírja az útvonaltervezés megvalósítását két kereszteződés között, vagy teljes útvonalt.
     * @param intersections az érintendő csúcspontok, ha
     *                      egy elemű: jelenlegi út kezdőpontja és megadott pont között legrövidebb,
     *                      ha két elemű: a két végpont közti legrövidebb út
     *                      különben teljes útvonal
     */
    public void SetRoute(List<Intersection> intersections);
    public String toList();
    public String printLong();

    /**
     * Ütközés kezelése: mindkét jármű megáll, a sáv BLOCKED állapotba kerül.
     * @param other a másik jármű, amellyel ütközés történt
     */
    public void Collide(IVehicle other);
}
