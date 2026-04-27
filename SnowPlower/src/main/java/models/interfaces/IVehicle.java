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
     * Előírja az útvonaltervezés megvalósítását két kereszteződés között.
     * @param start keződ kereszteződés
     * @param end keződ kereszteződés
     */
    public void SetRoute(List<Intersection> intersections);
    public int GetId();
}
