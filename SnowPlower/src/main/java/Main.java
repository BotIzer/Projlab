package main.java;


import main.java.gui.GameFrame;
import main.java.models.objects.Console;
import main.java.models.interfaces.ICommand;

/**
 * A program fő belépési pontja, amely a felhasználói felületet és a fő menüt kezeli.
 * Felelős a megfelelő használati esetek elindításáért.
 */
public class Main {

    private static ICommand console = new Console();

    public static void main(String[] args) {
        String line;
        try {
            boolean invalidInput = true;
            do{
                Console.print("Start game or Run tests?");
                Console.print("(0) Start game (konzol)");
                Console.print("(1) Run tests");
                Console.print("(2) GUI mód (Swing)");
                Console.print("""
                -----------------
                (x)Exit
                """);
                line = Console.readLine();
                switch (line) {
                    case "x" -> invalidInput = false;
                    case "0" -> invalidInput = false;
                    case "1" -> invalidInput = false;
                    case "2" -> invalidInput = false;
                    default -> {break;}
                }

            }   while (invalidInput);

            switch (line) {
                case "x":
                    break;
                case "0":
                    console.start();
                    break;
                case "1":
                    console.runTests();
                    break;
                case "2":
                    // Swing GUI indítása az EDT-n
                    javax.swing.SwingUtilities.invokeLater(() -> new GameFrame());
                    // Megvárjuk, amíg az ablak bezárodik (EDT fut)
                    // Program stays alive via Swing EDT
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
