package main.java.models.objects.vehicles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import main.java.models.interfaces.ICleaning;
import main.java.models.interfaces.ILane;
import main.java.models.objects.Console;
import main.java.models.objects.FileHandler;
import main.java.models.objects.vehicles.heads.BlowerHead;
import main.java.models.objects.vehicles.heads.IceBreakerHead;

/**
 * Speciális munkagép (hókotró), amely a sávok tisztítását végzi a felszerelt fejek segítségével.
 */
public class SnowPlower extends VehicleBase {
    
    private List<ICleaning> heads = new ArrayList<>();
    private ICleaning currentHead;

    private double saltStorage = 100.0; 
    private double keroseneStorage = 100.0;
    private double gravelStorage = 100.0;

    public SnowPlower(double bs){
        super(bs);
        Console.print("\t!<<create>>SnowPlower");
        heads.add(new BlowerHead(0));
        heads.add(new IceBreakerHead(1));
        currentHead = heads.get(0);
    }

    public void attach(ICleaning newHead){
        Console.print("\t-> SnowPlower.attach(newHead)");
        if (newHead != null && !heads.contains(newHead)) {
            heads.add(newHead);
            if (currentHead == null) {
                currentHead = newHead;
            }
        }
        Console.print("\t<- SnowPlower.attach(newHead)");
    }

    public void ChangeAttachment(int id) {
        Console.print("\t-> SnowPlower.ChangeAttachment(id)");
        if (id >= 0 && id < heads.size()) {
            currentHead = heads.get(id);
        }
        Console.print("\t<- SnowPlower.ChangeAttachment(id)");
    }

    /**
     * Aktiválja a tisztítási folyamatot az aktuális sávon a jelenlegi fejjel.
     */
    public void PerformCleaning() {
        Console.print("\t\t\t-> SnowPlower.PerformCleaning()");
        if (currentHead != null && lane != null) {
            // A fejre delegáljuk a feladatot, átadva neki a cél sávot és magát a hókotrót (erőforrásként)
            currentHead.Clean(lane, this);
        }
        Console.print("\t\t\t<- SnowPlower.PerformCleaning()");
    }

    /**
     * Csökkenti a sókészletet.
     * @return true, ha volt elég só, különben false.
     */
    public boolean ConsumeSalt(double amount) {
        Console.print("\t-> SnowPlower.ConsumeSalt(amount)");
        if (saltStorage >= amount) {
            saltStorage -= amount;
            Console.print("\t<- SnowPlower.ConsumeSalt(amount) [SUCCESS]");
            return true;
        }
        Console.print("\t<- SnowPlower.ConsumeSalt(amount) [FAIL - No Salt]");
        return false;
    }

    /**
     * Kilistázza a leltárban lévő kotrófejeket kiválasztás érdekében
     * (attach segédfüggvénye) 
     * @return
     */
    public String listHeads(){
        StringBuilder list = new StringBuilder();
        list.append("Heads in Inventory:\n");
        for (ICleaning head : heads) {
            Console.print("(" + heads.indexOf(head) + ")" + head.print() + "+");
            list.append("(").append(heads.indexOf(head)).append(")").append(head.print()).append("\n");
        }
        return list.toString();
    }


    /**
     * Üzemanyagot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kerozin, különben false.
     */
    public boolean ConsumeBioKerosene(double amount) {
        Console.print("\t-> SnowPlower.ConsumeBioKerosene(amount)");
        if (keroseneStorage >= amount) {
            keroseneStorage -= amount;
            Console.print("\t<- SnowPlower.ConsumeBioKerosene(amount) [SUCCESS]");
            return true;
        }
        Console.print("\t<- SnowPlower.ConsumeBioKerosene(amount) [FAIL - No Kerosene]");
        return false;
    }

    /**
     * Kavicsot von le a tartályból a munka végzése során.
     * @return true, ha volt elég kavics, különben false.
     */
    public boolean ConsumeGravel(double amount) {
        Console.print("\t-> SnowPlower.ConsumeGravel(amount)");
        if (gravelStorage >= amount) {
            gravelStorage -= amount;
            Console.print("\t<- SnowPlower.ConsumeGravel(amount) [SUCCESS]");
            return true;
        }
        Console.print("\t<- SnowPlower.ConsumeGravel(amount) [FAIL - No Gravel]");
        return false;
    }

   @Override
    public void Move() {
        Console.print("\t\t-> SnowPlower.Move()");
        // Mozgás előtt/közben takarítunk az aktuális sávon (TDA elv)
        PerformCleaning();
        // A szülő (VehicleBase) Move() metódusa elvégzi a tényleges haladást/pozíció váltást
        super.Move(); 
        Console.print("\t\t<- SnowPlower.Move()");
    }

    @Override
    public void Stop() {
        Console.print("\t-> SnowPlower.Stop()");
        super.Stop(); // Hívjuk az ősosztály Stop metódusát
        Console.print("\t<- SnowPlower.Stop()");
    }

   @Override
    public void Slipping() { 
        Console.print("\t-> SnowPlower.Slipping()");
        super.Slipping(); // Hívjuk az ősosztály Slipping metódusát
        Console.print("\t<- SnowPlower.Slipping()");
    }

   @Override
    public void SetRoute(List<ILane> validRoute) {
        Console.print("\t-> SnowPlower.SetRoute(route)");
        super.SetRoute(validRoute); // A VehicleBase menti el a route-t
        Console.print("\t<- SnowPlower.SetRoute(route)");
    } 

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("V");
        res.append("\nid=").append(id);
        res.append("\ntype=SnowPlower");
        res.append("\ncurrentPosition=" ).append(currentPosition);
        res.append("\nlane=" ).append(lane.toList());
        res.append("\nbaseSpeed=" ).append(baseSpeed);
        res.append("\nroute=");
        for (ILane lane : route) {
            res.append(lane.toList());
            res.append(";");
        }
        res.append("\ncurrentHead=").append(currentHead.toList());
        res.append("\nheads=");
        StringBuilder headString = new StringBuilder();
        for (ICleaning head : heads) {
            res.append(head.toList()).append(";");
            headString.append("\n").append(head.toString());
        }
        res.append(headString);
        return res.toString();
    }
    @Override
    public String printLong() {
        StringBuilder res = new StringBuilder(super.printLong());

        int sweeper = 0;
        int blower = 0;
        int icebreaker = 0;
        int dragon = 0;
        int salter = 0;
        int graveler =0; 
        
        for (ICleaning head : heads) {
            switch (head.print().toLowerCase()) {
                case "sweeperhead" -> sweeper++;
                case "blowerhead" -> blower++;
                case "icebreakerhead" -> icebreaker++;
                case "dragonhead" -> dragon++;
                case "salterhead" -> salter++;
                case "gravelerhead" -> graveler++; 
                default -> {break;}
            }
        }
        res.append("\n\tCurrentHead: ").append(currentHead.toList());
        res.append("\n\tHeads: Sweeper: ").append(sweeper)
           .append("\n\t       Blower: ").append(blower)
           .append("\n\t       Salter: ").append(salter)
           .append("\n\t       IceBreaker: ").append(icebreaker)
           .append("\n\t       Graveler: ").append(graveler)
           .append("\n\t       Dragon: ").append(dragon);
        return res.toString();
    }

    //Fileból betöltés, szinkronizáció
    private List<Integer> pendingHeads = new ArrayList<>();
    private Integer pendingHead;
    @Override
    protected void applyData(Map<String, String> data) {
        super.applyData(data);
        for (Map.Entry<String, String> entry : data.entrySet()) {
           switch (entry.getKey()) {
            case "currentHead" -> pendingHead = Integer.parseInt(entry.getValue());
            case "heads" -> pendingHeads = FileHandler.parseList(entry.getValue());
            default -> {break;}
           } 
        }
    }
    @Override
    public void resolve(Map<Integer, ILane> lanes, Map<Integer, ICleaning> headsTmp){
        if (pendingLane != null) lane = lanes.get(pendingLane);
        if (pendingHead != null) currentHead = headsTmp.get(pendingHead); 
        route = pendingRoute.stream()
            .map(lanes::get)
            .collect(Collectors.toCollection(ArrayList::new));
        heads = pendingHeads.stream()
            .map(headsTmp::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));
        pendingLane = null;
        pendingHead = null;
        pendingRoute.clear();
        pendingHeads.clear();
    }
    
}