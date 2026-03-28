package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import main.java.models.objects.Console;
import main.java.models.interfaces.ICommand;

/**
 * A program fő belépési pontja, amely a felhasználói felületet és a fő menüt kezeli.
 * Felelős a megfelelő használati esetek elindításáért.
 */
public class Main {
        
    private static ICommand console = new Console();

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            do{
                Console.print("Select a use-case to run!");
                Console.print("(1)Load game from state");
                Console.print("(2)Save game state");
                Console.print("(3)Set Route of Vehicle (and Clean if selected is a SnowPlower)");
                Console.print("(4)Collision of two Cars");
                Console.print("(5)Buy Equipment");
                Console.print("""
                -----------------
                (x)Exit
                """);
                line = br.readLine();
                handleInput(line);

            }   while (!line.equals("x"));  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleInput(String line){
        switch (line) {
            case "1":
                console.start();
                console.saveState();
                break;
            case "2":
                console.start();
                console.loadState();
                break;
            case "3":
                console.start();
                console.initGeneral();
                console.setRoute();
                break;
            case "4":
                console.start();
                console.initIcy(); 
                break;
            case "5":
                console.start();
                console.buyEquipment();
                break; 
            default:
                break;
        }
    }
}
