package main.java.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import main.java.models.interfaces.*;
import main.java.models.objects.Map;

/**
 * Palya rajzterulet - utszeru stilus.
 * Tarolja a LaneView / VehicleView listaakat.
 * paintComponent(): hatter -> savok -> csomópontok -> jarmuvek -> jelmagyzarat.
 * Egerkatintasra kivalasztja az adott jarmuvet.
 * IViewObserver: Map.loop() utan automatikus repaint().
 */
public class MapPanel extends JPanel implements IViewObserver {

    private final List<LaneView>    laneViews    = new ArrayList<>();
    private final List<VehicleView> vehicleViews = new ArrayList<>();
    private final java.util.Map<ILane, Point[]> laneEndpoints = new IdentityHashMap<>();
    private java.util.Map<Integer, Point> intersectionPositions = new HashMap<>();

    private VehicleView selectedView = null;
    private java.util.function.Consumer<IVehicle> onVehicleSelected = v -> {};

    private static final Color BG_COLOR   = new Color(235, 238, 235);
    private static final Color GRID_COLOR = new Color(210, 215, 210);

    public MapPanel() {
        setBackground(BG_COLOR);
        setPreferredSize(new Dimension(820, 520));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                VehicleView hit = findVehicleViewAt(e.getPoint());
                if (hit != null) {
                    if (selectedView != null) selectedView.setSelected(false);
                    selectedView = hit;
                    selectedView.setSelected(true);
                    onVehicleSelected.accept(hit.getVehicle());
                    repaint();
                } else {
                    if (selectedView != null) {
                        selectedView.setSelected(false);
                        selectedView = null;
                        repaint();
                    }
                }
            }
        });
    }

    public void setOnVehicleSelected(java.util.function.Consumer<IVehicle> cb) {
        this.onVehicleSelected = cb;
    }

    // Rebuild

    public void rebuild(Map gameMap, java.util.Map<Integer, Point> positions) {
        laneViews.clear();
        vehicleViews.clear();
        laneEndpoints.clear();
        intersectionPositions = positions;

        for (ILane lane : gameMap.getAllLanes()) {
            int startId = Integer.parseInt(lane.getStart().toList());
            int endId   = Integer.parseInt(lane.getEnd().toList());
            Point sp = positions.getOrDefault(startId, new Point(60, 60));
            Point ep = positions.getOrDefault(endId,   new Point(220, 60));
            LaneView lv = new LaneView(lane, sp, ep, this);
            laneViews.add(lv);
            laneEndpoints.put(lane, new Point[]{new Point(sp), new Point(ep)});
        }

        // Jarmuvek - offsetIndex alapjan szet van osztva savonkent
        java.util.Map<ILane, Integer> laneCount = new IdentityHashMap<>();
        for (IVehicle v : gameMap.getVehicles()) {
            ILane currentLane = (v instanceof main.java.models.objects.vehicles.VehicleBase vb)
                    ? vb.getLane() : null;
            int offset = laneCount.getOrDefault(currentLane, 0);
            vehicleViews.add(new VehicleView(v, laneEndpoints, offset, this));
            if (currentLane != null) laneCount.put(currentLane, offset + 1);
        }

        repaint();
    }

    // Painting

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawGrid(g2);

        for (LaneView lv : laneViews) lv.draw(g);

        for (java.util.Map.Entry<Integer, Point> e : intersectionPositions.entrySet()) {
            drawIntersection(g2, e.getValue(), e.getKey());
        }

        for (VehicleView vv : vehicleViews) vv.draw(g);

        drawLegend(g2);
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(GRID_COLOR);
        g2.setStroke(new BasicStroke(1f));
        int step = 40;
        for (int x = 0; x < getWidth();  x += step) g2.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += step) g2.drawLine(0, y, getWidth(), y);
    }

    private void drawIntersection(Graphics2D g2, Point p, int id) {
        int r = 16;
        g2.setColor(new Color(0, 0, 0, 45));
        g2.fillOval(p.x - r + 2, p.y - r + 2, r * 2, r * 2);
        g2.setColor(new Color(245, 245, 248));
        g2.fillOval(p.x - r, p.y - r, r * 2, r * 2);
        g2.setColor(new Color(70, 75, 85));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawOval(p.x - r, p.y - r, r * 2, r * 2);
        g2.setColor(new Color(90, 95, 110));
        g2.fillOval(p.x - 4, p.y - 4, 8, 8);
        g2.setColor(new Color(45, 50, 65));
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        FontMetrics fm = g2.getFontMetrics();
        String label = "I" + id;
        g2.drawString(label, p.x - fm.stringWidth(label) / 2, p.y - r - 3);
    }

    private void drawLegend(Graphics2D g2) {
        String[] states = {"CLEAN","SNOWY","SNOWY_DEEP","ICY","BROKEN_ICE","BLOCKED","GRAVELED"};
        int x = getWidth() - 130;
        int y = 12;
        g2.setColor(new Color(255, 255, 255, 210));
        g2.fillRoundRect(x - 6, y - 2, 126, states.length * 16 + 8, 8, 8);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        for (String s : states) {
            g2.setColor(LaneView.stateColor(s));
            g2.fillRect(x, y, 12, 11);
            g2.setColor(new Color(40, 40, 40));
            g2.drawString(s, x + 16, y + 10);
            y += 16;
        }
    }

    private VehicleView findVehicleViewAt(Point p) {
        // Fordított sorrend: legfelso (utoljara rajzolt) elso
        for (int i = vehicleViews.size() - 1; i >= 0; i--) {
            if (vehicleViews.get(i).getBounds().contains(p)) {
                return vehicleViews.get(i);
            }
        }
        return null;
    }

    @Override
    public void update(IObservable source) {
        SwingUtilities.invokeLater(this::repaint);
    }
}
