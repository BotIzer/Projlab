package main.java.models.objects;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import main.java.models.interfaces.ILane;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;

/**
 * Dijkstra-algoritmus alapú legrövidebb útkeresés az úthálózaton.
 * Csúcsok: Intersection, élek: ILane (súly: a lane-t tartalmazó Road hossza).
 */
public class Dijkstra {

    /**
     * Belső segédrekord a prioritásos sorhoz.
     */
    private record Entry(double dist, Intersection node) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry o) {
            return Double.compare(this.dist, o.dist);
        }
    }

    /**
     * Meghatározza a legrövidebb utat két kereszteződés között.
     *
     * @param map   a játéktérkép (tartalmazza az összes utat és kereszteződést)
     * @param start kezdő kereszteződés
     * @param end   cél kereszteződés
     * @return a legrövidebb utat alkotó ILane-ek listája (start→end sorrendben),
     *         vagy {@code null}, ha a cél nem érhető el
     */
    public static List<ILane> dijkstra(Map map, Intersection start, Intersection end) {
        if (map == null || start == null || end == null) return null;
        if (start == end) return new ArrayList<>();

        // IdentityHashMap: referencia-egyenlőség alapján különbözteti meg a csúcsokat
        // (az id nem feltétlenül egyedi a skeleton-inicializáláskor)
        IdentityHashMap<Intersection, Double> dist = new IdentityHashMap<>();
        IdentityHashMap<Intersection, ILane>  prev = new IdentityHashMap<>();

        for (Intersection i : map.getIntersections()) {
            dist.put(i, Double.MAX_VALUE);
            prev.put(i, null);
        }
        dist.put(start, 0.0);

        PriorityQueue<Entry> pq = new PriorityQueue<>();
        pq.add(new Entry(0.0, start));

        while (!pq.isEmpty()) {
            Entry current     = pq.poll();
            Intersection node = current.node();
            double nodeDist   = current.dist();

            if (node == end) break;

            // Elavult bejegyzés kihagyása
            if (nodeDist > dist.getOrDefault(node, Double.MAX_VALUE)) continue;

            // Szomszédos cross-roads: csak az adott csomóponthoz tartozó utakat nézzük
            for (Road road : node.getRoads()) {
                double weight = road.getLength();
                for (ILane lane : road.getLanes()) {
                    // Csak azok a sávok érdekelnek, amelyek ebből a csomópontból indulnak
                    if (lane.getStart() != node) continue;

                    Intersection neighbor = lane.getEnd();
                    double newDist = nodeDist + weight;

                    if (newDist < dist.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        dist.put(neighbor, newDist);
                        prev.put(neighbor, lane);
                        pq.add(new Entry(newDist, neighbor));
                    }
                }
            }
        }

        // Ha a cél elérhetetlen
        if (dist.getOrDefault(end, Double.MAX_VALUE) == Double.MAX_VALUE) return null;

        // Útvonal visszarekonstruálása prev-térképből
        LinkedList<ILane> path = new LinkedList<>();
        Intersection cursor = end;
        while (prev.get(cursor) != null) {
            ILane lane = prev.get(cursor);
            path.addFirst(lane);
            cursor = lane.getStart();
        }
        return path;
    }
}
