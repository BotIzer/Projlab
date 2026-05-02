package main.java.models.objects;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import main.java.models.interfaces.*;
import main.java.models.objects.road.Intersection;
import main.java.models.objects.road.Road;
import main.java.models.objects.vehicles.Bus;
import main.java.models.objects.vehicles.Car;
import main.java.models.objects.vehicles.SnowPlower;

/**
 * A felhasználói interakciókért és a parancsok feldolgozásáért felelős központi vezérlő osztály.
 */
public class Console implements ICommand {
    //vezérelt objektumok
    private Player player;
    private Map map;
    private IVehicle selectedVehicle;
    private Shop shop;
    private FileHandler fileHandler;
    //globális input olvasó
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //lokális konstansok
    private static final List<String> validCommands = List.of(
        "help", "load", "save", "select", "setRoute", "buy", "attach", "switch", "printState", "exit", "step" 
    );
    private static final String HELPMESSAGE = 
            """
            load <filename.ext> :load saved state
            save <filename.ext> :save current state
            select : select a Vehicle for operations
            setRoute [-f] : set route of selected vehicle
            buy : opens shop
            attach : attach head from inventory to selected vehicle (only if selected is SnowPlower)
            switch : switch current head used for cleaning on selected vehicle (only if selected is SnowPlower)
            printState [-f, -s] :lists state of game
            step : runs one cycle of the game
            """;

    /**
    * Standard kimenetre irja a paraméterben kapott szöveget
    *  @param msg Az átadott szöveg.
    */        
    public static void print(String msg){
        try {
            System.out.println(msg);
        } catch (Exception e) {
           e.printStackTrace(); 
        }
    }
    /**
     * Standard bemenetről olvas be egy sort
     * @return A beolvasott szöveg
     */
    public static String readLine(){
        String res = null;
        try {
            res = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Bezárja az olvasót
     */
    @Override
    public void closeReader(){
        try {
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    /**
     * Kezeli a felhasználó által adott bemeneteket, 
     * meghívja az azokhoz tartozó függvényeket, 
     * illetve egy hibaüzenetet, ha nem rendelhető parancs a bemenethez
     * @param cmd A felhasználó által megadott szöveg
     */
    public void input(String cmd){
        print("-> Console.input()");
        String[] tmp = cmd.trim().split("\\s+");
        if(tmp.length == 0) return;
        String command = tmp[0];
        List<String> args = Arrays.stream(tmp).skip(1).toList();
        List<String> matchingCommands = validCommands.stream()
                                                     .filter(cd -> cd.startsWith(command))
                                                     .toList();
        if (matchingCommands.isEmpty()){
            print("Invalid command! \nType help for list of commands, or help <command> for specific command!");
            return;
        } 
        if (matchingCommands.size() > 1) {
            Console.print("Command is ambigous, list of valid commands: \n\t" + String.join(",\n\t", matchingCommands));
            return;
        }
        execute(command, args);
        print("<- Console.input()");
    }
    /**
     *Segédfüggvény olvashatóságnak, input feldolgozása után meghivja a  
     *hozzárendelt függvényt
     * @param cmd Az input függvény által meghatározott parancs
     * @param args -II- argumentumjai
     */
    private void execute(String cmd, List<String> args){
        boolean isSelected = selectedVehicle != null;
        
        switch (cmd){
            case "help" -> print(HELPMESSAGE);
            case "load" -> loadState(args.isEmpty() ? "" : args.get(0));
            case "save" -> saveState(args.isEmpty() ? "" : args.get(0));
            case "select" -> selectVehicle();
            case "setRoute" -> setRoute();
            case "buy" -> buyEquipment();
            case "attach" -> attach(isSelected);
                
            case "switch" -> {
                if (isSelected && selectedVehicle instanceof SnowPlower plower) {
                    player.changeEquipment(plower);
                } else {
                    print("No vehicle of type SnowPlower is selected");
                }
            }
            case "printState" -> printState(args);
            case "exit" -> end(args);
            case "step" -> step();
            default -> print("Invalid command! \nType help for list of commands, or help <command> for specific command!");
        }
        return;
    }
    /**
     * Inicializálja a játékot
     * @return A művelet sikeres/sikertelen
     */
    @Override
    public boolean start() {
        print("-> Console.start()");
        player = new Player();
        map = new Map();
        fileHandler = new FileHandler();
        shop = new Shop();
        loop(); 
        print("<- Console.start():true");
        return true;
    }
    /**
     * A játék befejezése, rákérdez mentési szándékra
     * @return Mentett/nem mentett
     */

    @Override
    public boolean end(List<String> args) {
        print("-> Console.end()");
        boolean shouldSaveImmediately = args.contains("-s");
        String filename = ""; 
        boolean result = true; 

        if (!args.isEmpty()) {
            String last = args.get(args.size() - 1);
            filename = last.equals("-s") ? "save.txt" : last;
        } 

        if (shouldSaveImmediately) {
            saveState(filename);
        } else {
            print("Do you want to save the game? (y/n)");
            boolean confirm;
            try {
                String yn = readLine();
                confirm = yn != null && yn.equalsIgnoreCase("y");
            } catch (Exception e) {
                confirm = false;
            }
            if (confirm) result = saveState(filename);
        }
        print("<- Console.end(): " + result);
        return result;
    }


    /**
     * Elmenti a játék jelenlegi állapotát a paraméterben átadott fájlba
     * @param loc A mentési fájl, alapértelmezetten save.txt
     * @return A művelet sikeres/sikertelen
     */
    @Override
    public boolean saveState(String loc) {
        print("-> Console.saveState("+ loc +")");
        boolean res = true;
        String out = "<- Console.saveState("+ loc +"):";
        if(loc != null && !loc.isEmpty())
        {
            res = fileHandler.saveState(loc, player, map);
            out += res; 
            print(out);
            return res;
        }
        print("Save to: (default: save.txt)");
        try {
            loc = br.readLine();
            out = "<- Console.saveState("+ loc +"):";
        } catch (Exception e) {
            res = false;
            print(e.getMessage());
        }
        if (loc != null && !loc.equals("")) res = fileHandler.saveState(loc, player, map);
        out += res; 
        print(out);
        return res;
    }

    /**
     * Megpróbálja betölti a játék egy mentett állapotát
     * Felhasználót értesiti felmerülő hibákról
     * @param loc A betöltendő fájl, alapértelmezetten save.txt
     * @return A művelet sikeres/sikertelen
     */

    @Override
    public boolean loadState(String loc) {
        print("-> Console.loadState()");
    
        if (loc == null || loc.isEmpty()) {
            try {
                print("Enter file to load from: (default: save.txt)");
                loc = br.readLine();
                if (loc == null || loc.isEmpty()) loc = "save.txt";
            } catch (Exception e) {
                print("Input error: " + e.getMessage());
                return false;
            }
        }

        boolean success = fileHandler.loadState(loc, player, map);
        
        print("<- Console.loadState(): " + success);
        return success;

    }
    
    /**
     * Beállit egy útvonalat a kiválasztott járműnek
     * @return A művelet sikeres/sikertelen
     */
    @Override
    public boolean setRoute() {
        print("-> Console.setRoute(vehicle)");
        if (selectedVehicle == null) {
            print("No vehicle selected");
            print("<- Console.setRoute(vehicle): false");
            return false;
        }
        print("Select intersections: (enter x to end selection)");
        print(map.printInterSections());
        String input = "";
        ArrayList<Integer> ids = new ArrayList<>();
        do {
            input = readLine();
            input = (input == null) ? "x" : input.trim();
            if (!input.equals("x")) {
                try {
                    ids.add(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    print("Invalid input: '" + input + "' — enter a number or x to finish");
                }
            }
        } while (!input.equals("x"));

        if (ids.isEmpty()) {
            print("<- Console.setRoute(vehicle): false (no intersections selected)");
            return false;
        }
        List<Intersection> route = map.determineRoute(ids);
        selectedVehicle.SetRoute(route);
        print("<- Console.setRoute(vehicle): true");
        return true;
    }

    /**
     * Felkéri a felhasználót hogy válasszon ki egy járművet, amiken további műveletekez végezhet
     * @return A művelet sikeres/sikertelen
     */
    @Override
    public boolean selectVehicle() {
        print("-> Console.selectVehicle()");
        String id = null;
        print("Select a vehicle:");
        printVehicles();
        try {
            id = readLine();
            if(id != null){
                selectedVehicle = map.getVehicles().get(Integer.parseInt(id));
                if (selectedVehicle != null)print("<- Console.selectVehicle():true");
            }
        } catch (Exception e) {
            print(e.getMessage());
        }
        return true;
    }
    /**
     * Megnyitja a bolt vezérlőjét
     * @return A vásárlás sikeres/sikertelen
     */
    @Override
    public boolean buyEquipment() {
        print("-> Console.buyEquipment()");
        int plowersBefore = player.getPlowers().size();
        boolean res = shop.processPurchase(player);
        // Az újonnan vásárolt SnowPlower-eket a Map-hez is hozzáadjuk,
        // hogy mentéskor és Dijkstránál is látszódjanak.
        List<SnowPlower> plowers = player.getPlowers();
        for (int i = plowersBefore; i < plowers.size(); i++) {
            map.addVehicle(plowers.get(i));
        }
        print("<- Console.buyEquipment(): " + res);
        return res;
    }

    /**
     * Lecseréli a kiválasztott jármű takarításra használt fejét,
     * amennyiben a kiválasztott jármű Hókotró
     * @return A vásárlás sikeres/sikertelen
     */
    @Override
    public boolean changeEquipment() {
        print("-> Console.changeEquipment()");
        if (selectedVehicle == null || !(selectedVehicle instanceof SnowPlower plower)) {
            print("No SnowPlower selected");
            print("<- Console.changeEquipment(): false");
            return false;
        }
        boolean res = player.changeEquipment(plower);
        print("<- Console.changeEquipment(): " + res);
        return res;
    }
    /**
     * Kilistázza a pályán lévő autókat
     * @return Formátum: (sorszám kiválasztáshoz) type: (tipus(Bus/Car/SnowPlower)) 
     */
    @Override
    public String printVehicles() {
        print("-> Console.printVehicles()");
        StringBuilder list = new StringBuilder();
        var vehicles = map.getVehicles().stream().filter(Predicate.not(Car.class::isInstance)).collect(Collectors.toCollection(ArrayList::new));
        for (IVehicle vehicle : vehicles) {
            list.append("\n(");
            list.append(map.getVehicles().indexOf(vehicle));
            list.append(") type:");
            list.append(vehicle.getClass().getSimpleName());
        }
        print(list.toString());
        print("<- Console.printVehicles():String");
        return list.toString();
    }

    /**
     * Kilistázza a játékos leltárát
     * @return Formátum:
        Player: 
            balance: [jelenlegi pénzmennyiség] 
            plowers: [id1], [id2], [id3],…[idn] 
            buses: [id1], [id2], [id3],…[idn] 
            heads: Sweeper [n1], Blower [n2], Salter[n3], IceBreaker[n4], Graveler[n5], Dragon[n6]  
     */
    @Override
    public String printInventory() {
        print("-> Console.printInventory()");
        String inv = player.printInventory();
        print(inv);
        print("<- Console.printInventory():String");
        return inv;
    }
    /**
     * 
     * @param args argumentumok: -s: rövid, olvasható
     *                           -f: loadState által elfogadott formátum 
     */
    public void printState(List<String> args){
        if(args.contains("-s")){
            print(player.printInventory());
            print(map.print());
            if (selectedVehicle == null) print("selectedVehicle: ");
            else print("selectedVehicle: " + selectedVehicle.toList());
        } else if(args.contains("-f")){
            print(fileHandler.format(player, map));
        } else {
            print(player.printInventory());
            print(map.printLong());
        }
    }

    private void step(){
        map.loop();
    }

    @Override
    public void initGeneral(){
        Console.print("----------------Initialization--------------");
        ArrayList<ILane> lanes = new ArrayList<>();
        Road r = new Road(lanes, 5);
        ArrayList<Intersection> intersections = (ArrayList<Intersection>)r.initGeneral();
        map.addRoad(r);
        for (Intersection intersection : intersections) {
            map.addIntersections(intersection);
        }
        SnowPlower sp = new SnowPlower(45.0);
        Car c = new Car(50.0);
        ArrayList<Road> route = new ArrayList<>();
        Bus b = new Bus(40.0, "1", route);  
        map.addVehicle(sp);
        map.addVehicle(c);
        map.addVehicle(b);
        map.initGeneral(); 
        Console.print("------------End of Initialization-----------");
    }
    @Override
    public void initIcy(){
        Console.print("----------------Initialization--------------");
        ArrayList<ILane> lanes = new ArrayList<>();
        Road r = new Road(lanes, 5);
        ArrayList<Intersection> intersections = (ArrayList<Intersection>)r.initGeneral();
        map.addRoad(r);
        for (Intersection intersection : intersections) {
            map.addIntersections(intersection);
        }
        SnowPlower sp = new SnowPlower(45.0);
        Car c1 = new Car(50.0);
        Car c2 = new Car(30.0);
        ArrayList<Road> route = new ArrayList<>();
        Bus b = new Bus(40.0, "1", route);  
        map.addVehicle(sp);
        map.addVehicle(c1);
        map.addVehicle(c2);
        c1.SetRoute(new ArrayList<>());
        c2.SetRoute(new ArrayList<>());
        map.addVehicle(b);
        map.initIcy(); 
        Console.print("------------End of Initialization-----------");
    }
    @Override
    public void loop(){
        String input = "";
        do {
            input = readLine();
            input = (input == null) ? "" : input;
            input(input); 
        } while (!input.startsWith("e"));
        
    }
    @Override
    public void runTests() {
        TestRunner ts = new TestRunner();
        String input = "";
        do {
            print(TestRunner.TEST_MENU);
            try {
                input = br.readLine();
                int id = Integer.parseInt(input);             
                ts.runTests(id);
            } catch (Exception e) {
                print(e.getMessage());
            }
        } while (!input.equals("x"));
    }

    private void attach(boolean isSelected){
        if (isSelected && selectedVehicle instanceof SnowPlower plower) {
            String headList = player.listHeads();
            if (headList.isBlank()) {
                print("No heads in inventory. Buy some first with 'buy'.");
            } else {
                print(headList);
                String id = Console.readLine();
                try {
                    player.attach(plower, Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    print("Invalid input: '" + id + "' — enter a number");
                } catch (IndexOutOfBoundsException e) {
                    print("Invalid index: " + id);
                }
            }
        } else {
            print("No vehicle of type SnowPlower is selected");
        }
    }

}
