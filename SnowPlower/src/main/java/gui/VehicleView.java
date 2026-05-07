package main.java.gui;

import java.awt.*;
import java.util.Map;

import main.java.models.interfaces.ILane;
import main.java.models.interfaces.IObservable;
import main.java.models.interfaces.IVehicle;
import main.java.models.interfaces.IViewObserver;
import main.java.models.objects.vehicles.Bus;
import main.java.models.objects.vehicles.Car;
import main.java.models.objects.vehicles.SnowPlower;
import main.java.models.objects.vehicles.VehicleBase;

/**
 * Forgato, utszeru jarmu-ikon.
 * - Az ikon az utszakasz szogevel megegyezoen forgatva jelenik meg
 * - Pozicio: a sav menten offsetIndex alapjan elosztva
 * - Kis arnyek + kivalasztas kiemelés
 */
public class VehicleView implements IViewObserver {

    private static final int W_CAR  = 18, H_CAR  = 11;
    private static final int W_BUS  = 26, H_BUS  = 13;
    private static final int W_SP   = 22, H_SP   = 13;
    private static final int HIT_R  = 14;

    private final IVehicle vehicle;
    private final Map<ILane, Point[]> laneEndpoints;
    private final int  offsetIndex;
    private final MapPanel mapPanel;
    private boolean selected = false;

    public VehicleView(IVehicle vehicle,
                       Map<ILane, Point[]> laneEndpoints,
                       int offsetIndex,
                       MapPanel mapPanel) {
        this.vehicle       = vehicle;
        this.laneEndpoints = laneEndpoints;
        this.offsetIndex   = offsetIndex;
        this.mapPanel      = mapPanel;
        if (vehicle instanceof IObservable obs) obs.addObserver(this);
    }

    public void setSelected(boolean sel) { this.selected = sel; }
    public IVehicle getVehicle()         { return vehicle; }

    public Rectangle getBounds() {
        Point p = getCenter();
        if (p == null) return new Rectangle(0, 0, 0, 0);
        return new Rectangle(p.x - HIT_R, p.y - HIT_R, HIT_R * 2, HIT_R * 2);
    }

    private Point getCenter() {
        ILane lane = (vehicle instanceof VehicleBase vb) ? vb.getLane() : null;
        if (lane == null) return null;
        Point[] ep = laneEndpoints.get(lane);
        if (ep == null) return null;
        Point s = ep[0], e = ep[1];

        boolean arrived = (vehicle instanceof VehicleBase vb2) && vb2.getRoute().isEmpty();

        // arrived  -> sav vege fele:  0.68 / 0.80 / 0.92
        // menetben -> sav eleje fele: 0.18 / 0.32 / 0.44
        double baseT = arrived ? 0.68 : 0.18;
        double t     = baseT + (offsetIndex % 3) * 0.12;

        double angle    = Math.atan2(e.y - s.y, e.x - s.x);
        int    perpSign = (offsetIndex % 3) - 1;   // -1, 0, +1
        int    perp     = perpSign * 14 + (offsetIndex / 3) * 16;
        int    nx       = (int)(-Math.sin(angle) * perp);
        int    ny       = (int)( Math.cos(angle) * perp);

        return new Point(
            (int)(s.x + t * (e.x - s.x)) + nx,
            (int)(s.y + t * (e.y - s.y)) + ny
        );
    }

    private double getLaneAngle() {
        ILane lane = (vehicle instanceof VehicleBase vb) ? vb.getLane() : null;
        if (lane == null) return 0;
        Point[] ep = laneEndpoints.get(lane);
        if (ep == null) return 0;
        return Math.atan2(ep[1].y - ep[0].y, ep[1].x - ep[0].x);
    }

    public void draw(Graphics g) {
        Point center = getCenter();
        if (center == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double angle = getLaneAngle();
        int vid = (vehicle instanceof VehicleBase vb) ? vb.getId() : -1;

        if (selected) {
            g2.setColor(new Color(255, 165, 0, 200));
            g2.setStroke(new BasicStroke(3f));
            g2.drawOval(center.x - HIT_R, center.y - HIT_R, HIT_R * 2, HIT_R * 2);
        }

        if (vehicle instanceof SnowPlower sp) {
            drawSnowPlower(g2, center, angle, sp);
        } else if (vehicle instanceof Bus) {
            drawBus(g2, center, angle);
        } else if (vehicle instanceof Car) {
            drawCar(g2, center, angle, vid);
        } else {
            drawGeneric(g2, center);
        }

        // Red X overlay when vehicle is stuck on BLOCKED lane
        if (vehicle instanceof VehicleBase vb && vb.getRoute().isEmpty()) {
            ILane curLane = vb.getLane();
            if (curLane != null && "BLOCKED".equals(curLane.getState())) {
                g2.setColor(new Color(220, 30, 30, 230));
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(center.x - 7, center.y - 7, center.x + 7, center.y + 7);
                g2.drawLine(center.x + 7, center.y - 7, center.x - 7, center.y + 7);
            }
        }

        drawId(g2, center, vid);
    }

    private void drawSnowPlower(Graphics2D g2, Point c, double angle, SnowPlower sp) {
        Graphics2D gc = (Graphics2D) g2.create();
        gc.translate(c.x, c.y);
        gc.rotate(angle);

        gc.setColor(new Color(0, 0, 0, 50));
        gc.fillRoundRect(-W_SP / 2 + 2, -H_SP / 2 + 2, W_SP, H_SP, 5, 5);

        gc.setColor(new Color(255, 215, 0));
        gc.fillRoundRect(-W_SP / 2, -H_SP / 2, W_SP, H_SP, 5, 5);
        gc.setColor(new Color(180, 140, 0));
        gc.setStroke(new BasicStroke(1.2f));
        gc.drawRoundRect(-W_SP / 2, -H_SP / 2, W_SP, H_SP, 5, 5);

        gc.setColor(new Color(200, 200, 210));
        int[] px = { W_SP / 2, W_SP / 2 + 8, W_SP / 2 };
        int[] py = { -H_SP / 2, 0, H_SP / 2 };
        gc.fillPolygon(px, py, 3);
        gc.setColor(new Color(140, 140, 150));
        gc.setStroke(new BasicStroke(1f));
        gc.drawPolygon(px, py, 3);

        gc.setColor(Color.BLACK);
        gc.setFont(new Font("SansSerif", Font.BOLD, 8));
        gc.drawString("SP", -W_SP / 2 + 2, 4);

        String headName = sp.getCurrentHeadName().replace("Head", "");
        gc.setFont(new Font("SansSerif", Font.PLAIN, 6));
        gc.setColor(new Color(60, 60, 60));
        gc.drawString(headName.substring(0, Math.min(2, headName.length())),
                -W_SP / 2 + 2, H_SP / 2 - 1);

        gc.dispose();
    }

    private void drawBus(Graphics2D g2, Point c, double angle) {
        Graphics2D gc = (Graphics2D) g2.create();
        gc.translate(c.x, c.y);
        gc.rotate(angle);

        gc.setColor(new Color(0, 0, 0, 50));
        gc.fillRoundRect(-W_BUS / 2 + 2, -H_BUS / 2 + 2, W_BUS, H_BUS, 4, 4);

        gc.setColor(new Color(50, 115, 210));
        gc.fillRoundRect(-W_BUS / 2, -H_BUS / 2, W_BUS, H_BUS, 4, 4);
        gc.setColor(new Color(30, 80, 160));
        gc.setStroke(new BasicStroke(1.2f));
        gc.drawRoundRect(-W_BUS / 2, -H_BUS / 2, W_BUS, H_BUS, 4, 4);

        gc.setColor(new Color(200, 235, 255, 200));
        gc.fillRect(-W_BUS / 2 + 4, -H_BUS / 2 + 2, 7, H_BUS - 4);
        gc.fillRect( W_BUS / 2 - 11, -H_BUS / 2 + 2, 7, H_BUS - 4);

        gc.setColor(Color.WHITE);
        gc.setFont(new Font("SansSerif", Font.BOLD, 9));
        gc.drawString("B", -3, 5);

        gc.dispose();
    }

    private void drawCar(Graphics2D g2, Point c, double angle, int vid) {
        Color[] palette = {
            new Color(180, 60, 60),  new Color(60, 160, 60),
            new Color(60, 60, 200),  new Color(160, 80, 180),
            new Color(200, 130, 0),  new Color(0, 150, 170)
        };
        Color bodyColor = palette[Math.abs(vid) % palette.length];

        Graphics2D gc = (Graphics2D) g2.create();
        gc.translate(c.x, c.y);
        gc.rotate(angle);

        gc.setColor(new Color(0, 0, 0, 50));
        gc.fillRoundRect(-W_CAR / 2 + 2, -H_CAR / 2 + 2, W_CAR, H_CAR, 4, 4);

        gc.setColor(bodyColor);
        gc.fillRoundRect(-W_CAR / 2, -H_CAR / 2, W_CAR, H_CAR, 4, 4);
        gc.setColor(bodyColor.darker());
        gc.setStroke(new BasicStroke(1f));
        gc.drawRoundRect(-W_CAR / 2, -H_CAR / 2, W_CAR, H_CAR, 4, 4);

        gc.setColor(new Color(200, 230, 255, 180));
        gc.fillRect(-W_CAR / 2 + 10, -H_CAR / 2 + 2, 5, H_CAR - 4);

        gc.setColor(new Color(30, 30, 30));
        int wr = 3;
        gc.fillOval(-W_CAR / 2,         -H_CAR / 2 - 1,  wr * 2, wr * 2);
        gc.fillOval(-W_CAR / 2,          H_CAR / 2 - wr,  wr * 2, wr * 2);
        gc.fillOval( W_CAR / 2 - wr * 2, -H_CAR / 2 - 1,  wr * 2, wr * 2);
        gc.fillOval( W_CAR / 2 - wr * 2,  H_CAR / 2 - wr,  wr * 2, wr * 2);

        gc.dispose();
    }

    private void drawGeneric(Graphics2D g2, Point c) {
        g2.setColor(Color.MAGENTA);
        g2.fillOval(c.x - 10, c.y - 10, 20, 20);
    }

    private void drawId(Graphics2D g2, Point c, int vid) {
        if (vid < 0) return;
        String label = "#" + vid;
        g2.setFont(new Font("SansSerif", Font.BOLD, 9));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(label);
        int lx = c.x - tw / 2;
        int ly = c.y - HIT_R - 2;
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillRoundRect(lx - 2, ly - 9, tw + 4, 11, 3, 3);
        g2.setColor(new Color(40, 40, 40));
        g2.drawString(label, lx, ly);
    }

    @Override
    public void update(IObservable source) {
        javax.swing.SwingUtilities.invokeLater(mapPanel::repaint);
    }
}
