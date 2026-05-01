package main.java.models.objects;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import main.java.models.interfaces.ILane;
import main.java.models.objects.road.Intersection;

/**
 * Package-private Dijkstra-segédosztály — kizárólag a Map osztályon keresztül érhető el.
 *
 * TDA szemlélet:
 *  - Nem kér adatot a Map-től (nincs getRoads / getIntersections hívás).
 *  - Megmondja az Intersection-nak, hogy látogassa meg a saját kimenő sávjait
 *    (visitOutgoingLanes), az Intersection pedig megmondja az útnak (visitLanesFrom).
 *  - A sáv maga mondja meg, hol végződik (lane.getEnd()), és honnan indul (lane.getStart()) —
 *    ezek a sáv szemantikus tulajdonságai, nem belső implementációs részletek.
 */
class Dijkstra {

    /** Belső segédrekord a prioritásos sorhoz. */
    private record Entry(double dist, Intersection node) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry o) {
            return Double.compare(this.dist, o.dist);
        }
    }

    /**
     * Meghatározza a legrövidebb utat két kereszteződés között.
     * A Map paraméter szándékosan nincs jelen: az algoritmus kizárólag az
     * Intersection és Road objektumoknak "mond utasítást", nem kérdezi le a Map-et.
     *
     * @param start kezdő kereszteződés
     * @param end   cél kereszteződés
     * @return a legrövidebb utat alkotó ILane-ek listája (start→end sorrendben),
     *         vagy {@code null}, ha a cél nem érhető el
     */
    static List<ILane> dijkstra(Intersection start, Intersection end) {
        if (start == null || end == null) return null;
        if (start == end) return new ArrayList<>();

        // IdentityHashMap: referencia-egyenlőség alapján azonosítja a csomópontokat,
        // mivel az id mező skeleton-inicializáláskor nem feltétlenül egyedi.
        IdentityHashMap<Intersection, Double> dist = new IdentityHashMap<>();
        IdentityHashMap<Intersection, ILane>  prev = new IdentityHashMap<>();

        dist.put(start, 0.0);

        PriorityQueue<Entry> pq = new PriorityQueue<>();
        pq.add(new Entry(0.0, start));

        while (!pq.isEmpty()) {
            Entry current     = pq.poll();
            Intersection node = current.node();
            double nodeDist   = current.dist();

            if (node == end) break;

            // Elavult bejegyzés átugrása (lazy deletion)
            if (nodeDist > dist.getOrDefault(node, Double.MAX_VALUE)) continue;

            // TDA: Mondjuk meg az Intersection-nak, hogy látogassa meg a kimenő sávjait.
            // Az Intersection maga dönti el, melyik sávok indulnak belőle — mi csak
            // a callback logikáját adjuk meg.
            node.visitOutgoingLanes((lane, weight) -> {
                Intersection neighbor = lane.getEnd();
                double newDist = nodeDist + weight;
                if (newDist < dist.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, lane);
                    pq.add(new Entry(newDist, neighbor));
                }
            });
        }

        // Cél elérhetetlen
        if (!dist.containsKey(end)) return null;

        // Útvonal visszarekonstruálása: a sáv maga mondja meg, honnan indul
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
