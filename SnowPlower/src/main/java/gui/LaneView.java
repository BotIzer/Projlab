package main.java.gui;

import java.awt.*;
import main.java.models.interfaces.ILane;
import main.java.models.interfaces.IObservable;
import main.java.models.interfaces.IViewObserver;
import main.java.models.objects.road.BridgeLane;
import main.java.models.objects.road.TunnelLane;

/**
 * Utszeru sav-rajzolo.
 * - Aszfalt-alap (sotetszurke vastag vonal) + allapot-szin kozepso csik
 * - BridgeLane: korlátokkal, TunnelLane: felkor portalokkal
 * - Iranynyl a sav 75%-anal
 */
public class LaneView implements IViewObserver {

    private static final int ROAD_W  = 14;
    private static final int STATE_W = 7;
    private static final Color ASPHALT        = new Color(55, 58, 65);
    private static final Color ASPHALT_BRIDGE = new Color(120, 100, 70);
    private static final Color ASPHALT_TUNNEL = new Color(30, 32, 38);

    private final ILane    lane;
    final         Point    start;
    final         Point    end;
    private final MapPanel mapPanel;

    static Color stateColor(String state) {
        if (state == null) return new Color(160, 160, 160);
        return switch (state.toUpperCase()) {
            case "CLEAN"       -> new Color(60, 210, 80);
            case "SNOWY"       -> new Color(130, 180, 255);
            case "SNOWY_DEEP"  -> new Color(30,  80, 220);
            case "ICY"         -> new Color(0,   210, 220);
            case "BROKEN_ICE"  -> new Color(190, 50,  200);
            case "BLOCKED"     -> new Color(230, 30,  30);
            case "GRAVELED"    -> new Color(160, 90,  20);   // rich brown
            default            -> new Color(160, 160, 160);
        };
    }

    public LaneView(ILane lane, Point start, Point end, MapPanel mapPanel) {
        this.lane     = lane;
        this.start    = new Point(start);
        this.end      = new Point(end);
        this.mapPanel = mapPanel;
        if (lane instanceof IObservable obs) obs.addObserver(this);
    }

    public void draw(Graphics g) {
        Graphics2D g2   = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        String state  = lane.getState();
        Color  stCol  = stateColor(state);
        Stroke saved  = g2.getStroke();

        if (lane instanceof TunnelLane) {
            drawTunnel(g2, stCol);
        } else if (lane instanceof BridgeLane) {
            drawBridge(g2, stCol);
        } else {
            drawRoad(g2, stCol);
        }

        drawArrow(g2, stCol);
        drawLabel(g2, state);
        g2.setStroke(saved);
    }

    private void drawRoad(Graphics2D g2, Color stCol) {
        g2.setColor(new Color(0, 0, 0, 40));
        g2.setStroke(new BasicStroke(ROAD_W + 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(start.x + 2, start.y + 2, end.x + 2, end.y + 2);
        g2.setColor(ASPHALT);
        g2.setStroke(new BasicStroke(ROAD_W, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(start.x, start.y, end.x, end.y);
        g2.setColor(stCol);
        g2.setStroke(new BasicStroke(STATE_W, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(start.x, start.y, end.x, end.y);
    }

    private void drawBridge(Graphics2D g2, Color stCol) {
        g2.setColor(ASPHALT_BRIDGE);
        g2.setStroke(new BasicStroke(ROAD_W, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g2.drawLine(start.x, start.y, end.x, end.y);
        float[] dash = {13f, 6f};
        g2.setColor(stCol);
        g2.setStroke(new BasicStroke(STATE_W, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10f, dash, 0f));
        g2.drawLine(start.x, start.y, end.x, end.y);
        double angle = Math.atan2(end.y - start.y, end.x - start.x);
        int nx = (int)(-Math.sin(angle) * (ROAD_W / 2 + 1));
        int ny = (int)( Math.cos(angle) * (ROAD_W / 2 + 1));
        g2.setColor(new Color(100, 85, 55));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(start.x + nx, start.y + ny, end.x + nx, end.y + ny);
        g2.drawLine(start.x - nx, start.y - ny, end.x - nx, end.y - ny);
    }

    private void drawTunnel(Graphics2D g2, Color stCol) {
        g2.setColor(ASPHALT_TUNNEL);
        g2.setStroke(new BasicStroke(ROAD_W + 6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(start.x, start.y, end.x, end.y);
        g2.setColor(stCol.darker());
        g2.setStroke(new BasicStroke(STATE_W, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(start.x, start.y, end.x, end.y);
        drawPortal(g2, start);
        drawPortal(g2, end);
    }

    private void drawPortal(Graphics2D g2, Point p) {
        g2.setColor(new Color(70, 72, 80));
        g2.fillArc(p.x - 11, p.y - 11, 22, 22, 0, 180);
        g2.setColor(new Color(110, 115, 125));
        g2.setStroke(new BasicStroke(2f));
        g2.drawArc(p.x - 11, p.y - 11, 22, 22, 0, 180);
    }

    private void drawArrow(Graphics2D g2, Color stCol) {
        double dx  = end.x - start.x;
        double dy  = end.y - start.y;
        double len = Math.hypot(dx, dy);
        if (len < 40) return;
        double t     = 0.72;
        double ax    = start.x + t * dx;
        double ay    = start.y + t * dy;
        double angle = Math.atan2(dy, dx);
        int    sz    = 9;
        int[] xs = {
            (int)(ax + Math.cos(angle) * sz),
            (int)(ax + Math.cos(angle + 2.5) * sz * 0.65),
            (int)(ax + Math.cos(angle - 2.5) * sz * 0.65)
        };
        int[] ys = {
            (int)(ay + Math.sin(angle) * sz),
            (int)(ay + Math.sin(angle + 2.5) * sz * 0.65),
            (int)(ay + Math.sin(angle - 2.5) * sz * 0.65)
        };
        g2.setColor(new Color(255, 255, 255, 160));
        g2.fillPolygon(xs, ys, 3);
    }

    private void drawLabel(Graphics2D g2, String state) {
        int laneId = Integer.parseInt(lane.toList());
        int mx     = (start.x + end.x) / 2;
        int my     = (start.y + end.y) / 2;
        double angle = Math.atan2(end.y - start.y, end.x - start.x);
        int ox = (int)(-Math.sin(angle) * 16);
        int oy = (int)( Math.cos(angle) * 16);
        String label = "L" + laneId + " - " + state;
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(label);
        int lx = mx + ox - tw / 2;
        int ly = my + oy + 4;
        g2.setColor(new Color(255, 255, 255, 190));
        g2.fillRoundRect(lx - 3, ly - 11, tw + 6, 14, 5, 5);
        g2.setColor(new Color(40, 40, 40));
        g2.drawString(label, lx, ly);
    }

    public Point getStart() { return new Point(start); }
    public Point getEnd()   { return new Point(end);   }

    public Point getMidpoint() {
        return new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
    }

    public ILane getLane() { return lane; }

    @Override
    public void update(IObservable source) {
        javax.swing.SwingUtilities.invokeLater(mapPanel::repaint);
    }
}
