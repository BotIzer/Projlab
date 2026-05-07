package main.java.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Görgethető napló-panel. A Console.print() üzenetei ide kerülnek GUI módban.
 */
public class LogPanel extends JPanel {

    private final JTextArea logArea;
    private static final int MAX_LINES = 500;

    public LogPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Napló"));
        setPreferredSize(new Dimension(0, 130));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        logArea.setBackground(new Color(30, 30, 30));
        logArea.setForeground(new Color(200, 255, 200));

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);
    }

    /** Hozzáfűz egy sort a naplóhoz és az utolsó sorra görget. */
    public void log(String msg) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg + "\n");
            // Maximális sorok betartása
            String text = logArea.getText();
            String[] lines = text.split("\n", -1);
            if (lines.length > MAX_LINES) {
                StringBuilder sb = new StringBuilder();
                for (int i = lines.length - MAX_LINES; i < lines.length; i++) {
                    sb.append(lines[i]).append("\n");
                }
                logArea.setText(sb.toString());
            }
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    /** Visszaadja a log-sink Consumer-t a Console.setLogSink()-hez. */
    public java.util.function.Consumer<String> asSink() {
        return this::log;
    }
}
