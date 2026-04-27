package main.java;


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
            do{
                Console.print("Start game or Run tests?");
                Console.print("(0) Start game");
                Console.print("(1) Run tests");
                Console.print("""
                -----------------
                (x)Exit
                """);
                line = Console.readLine();

            }   while (!line.equals("x") || !line.equals("0") || !line.equals("1"));  

            switch (line) {
                case "x":
                    break;
            
                case "0":
                    console.start();
                    break;
                case "1":
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Console.br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleInput(String line){
        switch (line) {
            case "0":
                break;
            case "1":
                break;
            default:
                break;
        }
    }
}
