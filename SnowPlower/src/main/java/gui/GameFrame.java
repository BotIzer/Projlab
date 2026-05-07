package main.java.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import main.java.models.objects.Console;
import main.java.models.objects.Map;
import main.java.models.objects.Player;
import main.java.models.objects.road.Intersection;

/**
 * Foablak - utszeru vizualizacio + auto-lepctetcs timer.
 */
public class GameFrame extends JFrame {

    private final Console      console;
    private final MapPanel     mapPanel;
    private final LogPanel     logPanel;
    private final InfoPanel    infoPanel;
    private final ControlPanel controlPanel;

    private javax.swing.Timer autoTimer;
    private boolean            running = false;
    private JButton            btnPlayPause;
    private JLabel             stepLabel;
    private int                stepCount = 0;

    private static final int[]    SPEEDS    = {2000, 1000, 500, 200};
    private static final String[] SPEED_LBL = {"Lassu (2 s)", "Normal (1 s)", "Gyors (0.5 s)", "Turbo (0.2 s)"};

    public GameFrame() {
        super("SnowPlower Szimulaciо");

        console      = new Console();
        mapPanel     = new MapPanel();
        logPanel     = new LogPanel();
        infoPanel    = new InfoPanel();
        controlPanel = new ControlPanel();

        Console.setLogSink(logPanel.asSink());
        console.reset();
        controlPanel.init(console, infoPanel);

        mapPanel.setOnVehicleSelected(vehicle -> {
            int idx = console.getMap() != null
                    ? console.getMap().getVehicles().indexOf(vehicle) : -1;
            if (idx >= 0) console.selectVehicleById(idx);
            controlPanel.setSelectedVehicle(vehicle);
        });

        autoTimer = new javax.swing.Timer(SPEEDS[1], e -> tick());

        buildLayout();
        buildMenu();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Layout

    private void buildLayout() {
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(infoPanel,    BorderLayout.NORTH);
        eastPanel.add(controlPanel, BorderLayout.CENTER);
        eastPanel.setPreferredSize(new Dimension(215, 0));
        eastPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(200, 205, 200)));

        JPanel center = new JPanel(new BorderLayout());
        center.add(buildToolBar(), BorderLayout.NORTH);
        center.add(mapPanel,       BorderLayout.CENTER);
        center.add(logPanel,       BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(center,    BorderLayout.CENTER);
        getContentPane().add(eastPanel, BorderLayout.EAST);
    }

    private JToolBar buildToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.setBackground(new Color(245, 247, 245));
        tb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 205, 200)));

        btnPlayPause = new JButton("Play");
        btnPlayPause.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnPlayPause.setFocusPainted(false);
        btnPlayPause.setBackground(new Color(70, 180, 90));
        btnPlayPause.setForeground(Color.WHITE);
        btnPlayPause.setOpaque(true);
        btnPlayPause.addActionListener(e -> toggleTimer());

        JComboBox<String> speedBox = new JComboBox<>(SPEED_LBL);
        speedBox.setSelectedIndex(1);
        speedBox.setMaximumSize(new Dimension(140, 28));
        speedBox.addActionListener(e -> autoTimer.setDelay(SPEEDS[speedBox.getSelectedIndex()]));

        JButton btnStep = new JButton("Step (Space)");
        btnStep.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnStep.setFocusPainted(false);
        btnStep.addActionListener(e -> doStep());

        getRootPane().registerKeyboardAction(e -> doStep(),
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        stepLabel = new JLabel("  Steps: 0");
        stepLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        stepLabel.setForeground(new Color(80, 85, 80));

        tb.add(btnPlayPause);
        tb.addSeparator(new Dimension(8, 0));
        tb.add(new JLabel("  Speed: "));
        tb.add(speedBox);
        tb.addSeparator(new Dimension(12, 0));
        tb.add(btnStep);
        tb.addSeparator(new Dimension(12, 0));
        tb.add(stepLabel);

        return tb;
    }

    // Menu

    private void buildMenu() {
        JMenuBar mb = new JMenuBar();

        JMenu mFile = new JMenu("File");
        JMenuItem miLoad = new JMenuItem("Load... (Ctrl+O)");
        miLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        miLoad.addActionListener(e -> loadGame());
        JMenuItem miSave = new JMenuItem("Save... (Ctrl+S)");
        miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        miSave.addActionListener(e -> saveGame());
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.addActionListener(e -> System.exit(0));
        mFile.add(miLoad); mFile.add(miSave); mFile.addSeparator(); mFile.add(miExit);

        JMenu mGame = new JMenu("Game");
        JMenuItem miInit = new JMenuItem("Load save.txt");
        miInit.addActionListener(e -> initDefault());
        JMenuItem miStop = new JMenuItem("Stop Timer");
        miStop.addActionListener(e -> stopTimer());
        mGame.add(miInit); mGame.add(miStop);

        mb.add(mFile); mb.add(mGame);
        setJMenuBar(mb);
    }

    // Timer

    private void toggleTimer() {
        if (running) stopTimer(); else startTimer();
    }

    private void startTimer() {
        if (console.getMap() == null) return;
        running = true;
        autoTimer.start();
        btnPlayPause.setText("Pause");
        btnPlayPause.setBackground(new Color(220, 60, 60));
        btnPlayPause.setForeground(Color.WHITE);
    }

    private void stopTimer() {
        running = false;
        autoTimer.stop();
        btnPlayPause.setText("Play");
        btnPlayPause.setBackground(new Color(70, 180, 90));
        btnPlayPause.setForeground(Color.WHITE);
    }

    private void tick() {
        if (console.getMap() == null) { stopTimer(); return; }
        console.getMap().loop();
        stepCount++;
        stepLabel.setText("  Steps: " + stepCount);
    }

    private void doStep() {
        if (console.getMap() == null) return;
        if (running) stopTimer();
        tick();
    }

    // File ops

    private void loadGame() {
        stopTimer();
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Save files (*.txt)", "txt"));
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        rebuildViews(fc.getSelectedFile().getAbsolutePath());
    }

    private void saveGame() {
        if (console.getMap() == null) return;
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Save files (*.txt)", "txt"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        String path = fc.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".txt")) path += ".txt";
        console.saveState(path);
    }

    private void initDefault() {
        stopTimer();
        rebuildViews("save.txt");
    }

    // View rebuild

    public void rebuildViews(String saveFile) {
        stopTimer();
        stepCount = 0;
        stepLabel.setText("  Steps: 0");
        controlPanel.setSelectedVehicle(null);

        console.reset();
        if (saveFile != null) console.loadState(saveFile);
        Map gameMap = console.getMap();
        if (gameMap == null) {
            JOptionPane.showMessageDialog(this,
                    "Could not load: " + saveFile, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        gameMap.repairConnections();
        gameMap.addObserver(mapPanel);

        // Layout file = same name but .layout.txt extension
        String layoutFile = (saveFile != null)
                ? saveFile.replaceFirst("(\\.txt)?$", ".layout.txt")
                : null;
        java.util.List<Intersection> intersections = gameMap.getIntersections();
        java.util.Map<Integer, Point> positions;
        if (layoutFile != null) {
            positions = LayoutLoader.load(layoutFile, intersections);
        } else {
            positions = LayoutLoader.load("", intersections);
        }

        mapPanel.rebuild(gameMap, positions);

        Player player = console.getPlayer();
        if (player != null) infoPanel.setPlayer(player);
    }
}
