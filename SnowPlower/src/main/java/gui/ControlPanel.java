package main.java.gui;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import main.java.models.interfaces.IVehicle;
import main.java.models.objects.Console;
import main.java.models.objects.vehicles.SnowPlower;
import main.java.models.objects.vehicles.VehicleBase;

/**
 * Vezérlőpanel: Step, Útvonal, Vásárlás, Felszerelés gombok.
 * A GameFrame-től kap Console-referenciát és InfoPanel-t.
 */
public class ControlPanel extends JPanel {

    private final JLabel vehicleLabel;
    private final JButton btnRoute;
    private final JButton btnBuy;
    private final JButton btnAttach;
    private final JButton btnSwitch;

    private Console console;
    private InfoPanel infoPanel;

    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Vezérlés"));
        setPreferredSize(new Dimension(200, 0));

        vehicleLabel = new JLabel("<html><b>Jármű:</b> –</html>");
        vehicleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnRoute  = new JButton("🗺 Útvonal...");
        btnBuy    = new JButton("🛒 Vásárlás...");
        btnAttach = new JButton("🔩 Fej felszerelése...");
        btnSwitch = new JButton("🔄 Fej váltása...");

        for (JButton b : new JButton[]{btnRoute, btnBuy, btnAttach, btnSwitch}) {
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        }

        add(Box.createVerticalStrut(8));
        add(vehicleLabel);
        add(Box.createVerticalStrut(10));
        add(btnRoute);
        add(Box.createVerticalStrut(4));
        add(btnBuy);
        add(Box.createVerticalStrut(4));
        add(btnAttach);
        add(Box.createVerticalStrut(4));
        add(btnSwitch);
        add(Box.createVerticalGlue());

        // Disable SP-only buttons initially
        btnAttach.setEnabled(false);
        btnSwitch.setEnabled(false);
        btnRoute.setEnabled(false);

        wireButtons();
    }

    /** Beköti a Console-t és az InfoPanel-t. */
    public void init(Console c, InfoPanel ip) {
        this.console   = c;
        this.infoPanel = ip;
    }

    /** Frissíti a kiválasztott jármű feliratát. */
    public void setSelectedVehicle(IVehicle v) {
        if (v == null) {
            vehicleLabel.setText("<html><b>Jármű:</b> –</html>");
            btnAttach.setEnabled(false);
            btnSwitch.setEnabled(false);
            btnRoute.setEnabled(false);
        } else {
            int id = (v instanceof VehicleBase vb) ? vb.getId() : -1;
            boolean isSP = v instanceof SnowPlower;
            vehicleLabel.setText("<html><b>Jármű:</b> #" + id
                    + " " + v.getClass().getSimpleName() + "</html>");
            btnAttach.setEnabled(isSP);
            btnSwitch.setEnabled(isSP);
            btnRoute.setEnabled(true);
        }
    }

    // ── Gombok bekötése ───────────────────────────────────────────

    private void wireButtons() {

        // Útvonal beállítása
        btnRoute.addActionListener(e -> {
            if (console == null || console.getSelectedVehicle() == null) return;

            String intersections = console.getMap() != null
                    ? console.getMap().printInterSections() : "";
            String input = JOptionPane.showInputDialog(this,
                    "Metszéspont-azonosítók (szóközzel elválasztva):\n" + intersections,
                    "Útvonal beállítása",
                    JOptionPane.PLAIN_MESSAGE);

            if (input == null || input.isBlank()) return;
            List<Integer> ids = new ArrayList<>();
            for (String tok : input.trim().split("\\s+")) {
                try { ids.add(Integer.parseInt(tok)); }
                catch (NumberFormatException ex) { /* skip */ }
            }
            boolean ok = console.setRouteForSelected(ids);
            if (!ok) JOptionPane.showMessageDialog(this,
                    "Nem sikerült útvonalat beállítani.", "Hiba", JOptionPane.ERROR_MESSAGE);
        });

        // Vásárlás – egyszerű dialógus a Shop itemeknek megfelelően
        btnBuy.addActionListener(e -> {
            if (console == null) return;
            String[] items = {
                "(0) SnowPlower – 500 Ft",
                "(1) SweeperHead – 50 Ft",
                "(2) BlowerHead – 100 Ft",
                "(3) SalterHead – 200 Ft",
                "(4) IceBreakerHead – 200 Ft",
                "(5) DragonHead – 500 Ft",
                "(6) GravelerHead – 150 Ft"
            };
            String choice = (String) JOptionPane.showInputDialog(this,
                    "Válassz terméket:", "Vásárlás",
                    JOptionPane.PLAIN_MESSAGE, null, items, items[1]);
            if (choice == null) return;
            int itemId = Integer.parseInt(choice.substring(1, 2));

            String amtStr = JOptionPane.showInputDialog(this, "Mennyiség:", "1");
            if (amtStr == null) return;
            int amount;
            try { amount = Integer.parseInt(amtStr.trim()); }
            catch (NumberFormatException ex) { return; }

            // Simulation via Console with piped input
            String fakeInput = itemId + "\n" + amount + "\n";
            Console.setReader(new java.io.ByteArrayInputStream(fakeInput.getBytes()));
            console.buyEquipment();
            // Restore stdin
            Console.setReader(System.in);
            if (infoPanel != null) infoPanel.refresh();
        });

        // Fej felszerelése SP-re
        btnAttach.addActionListener(e -> {
            if (console == null) return;
            IVehicle sel = console.getSelectedVehicle();
            if (!(sel instanceof SnowPlower)) return;

            String headList = console.getPlayer().listHeads();
            if (headList == null || headList.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Nincsenek fejek a leltárban. Vásárolj előbb!",
                        "Leltár üres", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String idxStr = JOptionPane.showInputDialog(this,
                    "Válassz fejet (index):\n" + headList, "Fej felszerelése",
                    JOptionPane.PLAIN_MESSAGE);
            if (idxStr == null) return;
            try {
                int idx = Integer.parseInt(idxStr.trim());
                boolean ok = console.attachToSelected(idx);
                if (!ok) JOptionPane.showMessageDialog(this,
                        "Érvénytelen index.", "Hiba", JOptionPane.ERROR_MESSAGE);
                else if (infoPanel != null) infoPanel.refresh();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nem szám: " + idxStr);
            }
        });

        // Fej váltása SP-en
        btnSwitch.addActionListener(e -> {
            if (console == null) return;
            IVehicle sel = console.getSelectedVehicle();
            if (!(sel instanceof SnowPlower sp)) return;

            String headList = sp.listHeads();
            if (headList == null || headList.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Nincsenek fejek a hókotróra szerelve.",
                        "Nincs fej", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String idxStr = JOptionPane.showInputDialog(this,
                    "Válassz aktív fejet (index):\n" + headList, "Fej váltása",
                    JOptionPane.PLAIN_MESSAGE);
            if (idxStr == null) return;
            try {
                int idx = Integer.parseInt(idxStr.trim());
                boolean ok = console.switchHeadForSelected(idx);
                if (!ok) JOptionPane.showMessageDialog(this,
                        "Érvénytelen index.", "Hiba", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nem szám: " + idxStr);
            }
        });
    }
}
