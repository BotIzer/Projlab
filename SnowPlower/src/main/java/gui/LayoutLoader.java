package main.java.gui;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.java.models.objects.road.Intersection;

/**
 * Betölti a metszéspontok képernyő-koordinátáit egy .layout.txt fájlból.
 * Ha a fájl nem létezik, automatikusan generál lineáris elrendezést.
 *
 * Fájlformátum (save.layout.txt):
 *   # comment
 *   0=80,250
 *   1=280,250
 *   ...
 */
public class LayoutLoader {

    private static final int SPACING_X = 180;
    private static final int SPACING_Y = 160;
    private static final int ORIGIN_X  = 80;
    private static final int ORIGIN_Y  = 200;

    /**
     * Megpróbálja betölteni a layoutot a megadott fájlból.
     * Ha a fájl nem létezik / hibás, visszaesik az auto-layoutra.
     *
     * @param layoutFile pl. "save.layout.txt"
     * @param intersections a pálya metszéspontjainak listája (sorrend = id)
     */
    public static Map<Integer, Point> load(String layoutFile,
                                           List<Intersection> intersections) {
        File f = new File(layoutFile);
        if (f.exists()) {
            try {
                return loadFromFile(f);
            } catch (Exception e) {
                System.err.println("[LayoutLoader] Hiba a fájl olvasásakor: " + e.getMessage());
            }
        }
        return autoLayout(intersections);
    }

    private static Map<Integer, Point> loadFromFile(File f) throws Exception {
        Map<Integer, Point> result = new HashMap<>();
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                int id = Integer.parseInt(parts[0].trim());
                String[] coords = parts[1].trim().split(",", 2);
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());
                result.put(id, new Point(x, y));
            }
        }
        return result;
    }

    /**
     * Egyszerű automatikus elrendezés: metszéspontok vízszintes sorba, egyenlő távolságra.
     */
    private static Map<Integer, Point> autoLayout(List<Intersection> intersections) {
        Map<Integer, Point> result = new HashMap<>();
        int cols = (int) Math.ceil(Math.sqrt(intersections.size()));
        for (int i = 0; i < intersections.size(); i++) {
            int id = Integer.parseInt(intersections.get(i).toList());
            int col = i % cols;
            int row = i / cols;
            result.put(id, new Point(ORIGIN_X + col * SPACING_X,
                                     ORIGIN_Y + row * SPACING_Y));
        }
        return result;
    }
}
