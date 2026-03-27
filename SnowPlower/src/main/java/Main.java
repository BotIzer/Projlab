package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import main.java.models.objects.Console;
import main.java.models.interfaces.ICommand;

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
                Console.print("(3)Set Route of Vehicle");
                Console.print("(4)Clean Route");
                Console.print("(5)Cycle snow state");
                Console.print("(6)Road becomes Icy");
                Console.print("(7)Collision of two Cars");
                Console.print("(8)Collision of Car and Bus");
                Console.print("""
                -----------------
                (x)Exit
                """);
                line = br.readLine();
                handleInput(line);

            }   while (line.equals("x"));  
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
                
                break;
            case "3":
                
                break;
            case "4":
                
                break;
            case "5":
                
                break;
            case "6":
                
                break;
            case "7":
                
                break;
            case "8":
                    
            break;
            default:
                break;
        }
    }
}
