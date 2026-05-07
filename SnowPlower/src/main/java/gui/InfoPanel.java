package main.java.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import main.java.models.interfaces.IObservable;
import main.java.models.interfaces.IViewObserver;
import main.java.models.objects.Player;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * Megjeleniti a játékos egyenlegét és leltárát.
 * Figyeli a Player-t (IViewObserver): minden pénzügyi változásnál frissül.
 */
public class InfoPanel extends JPanel implements IViewObserver {

    private final JLabel balanceLabel;
    private final JLabel plowersLabel;
    private final JLabel headsLabel;
    private Player player;

    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Játékos"));
        setPreferredSize(new Dimension(200, 0));

        balanceLabel = new JLabel("Egyenleg: –");
        plowersLabel = new JLabel("Hókotrók: –");
        headsLabel   = new JLabel("<html>Fejek: –</html>");

        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        plowersLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        headsLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        add(Box.createVerticalStrut(8));
        add(balanceLabel);
        add(Box.createVerticalStrut(6));
        add(plowersLabel);
        add(Box.createVerticalStrut(4));
        add(headsLabel);
        add(Box.createVerticalGlue());
    }

    /** Bekötés a Player-hez (observer-regisztráció). */
    public void setPlayer(Player p) {
        if (this.player != null) this.player.removeObserver(this);
        this.player = p;
        p.addObserver(this);
        refresh();
    }

    /** Frissíti a kijelzett adatokat a Player aktuális állapota alapján. */
    public void refresh() {
        if (player == null) return;
        SwingUtilities.invokeLater(() -> {
            balanceLabel.setText("Egyenleg: " + player.getBalance() + " Ft");

            List<SnowPlower> plowers = player.getPlowers();
            plowersLabel.setText("Hókotrók: " + plowers.size());

            // Fejek listája a Player leltárából
            String headsText = player.listHeads();
            if (headsText == null || headsText.isBlank()) {
                headsLabel.setText("<html>Fejek: (üres)</html>");
            } else {
                headsLabel.setText("<html>Fejek:<br>"
                        + headsText.replace("\n", "<br>") + "</html>");
            }
        });
    }

    @Override
    public void update(IObservable source) {
        refresh();
    }
}
