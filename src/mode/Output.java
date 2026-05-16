package mode;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
 
import inventory.Inventory;
import restaurant.OrderList;
import utilities.Color;
import utilities.IO;
import utilities.ScoreRepository;
import utilities.Table;
import utilities.TableColumn;
 
// (O-03) CHANGED: Output now extends Mode and conforms to the shared interface.
public class Output extends Mode {
 
    private String name;
    private Inventory inventory;
    private OrderList orderList;
    private int timeTilNextOrder;
 
    public Output() {
        askForName("");
        this.inventory = new Inventory();
        this.inventory.setFreshInventory();
        orderList = new OrderList();
        this.timeTilNextOrder = 1;
        loop();
    }
 
    public void askForName(String err) {
        printError(err);
        System.out.print(Color.id(255) + "Please enter your name (Cannot be empty): " + Color.id(226));
        this.name = IO.scan.nextLine();
        if (this.name.isEmpty()) askForName("Name cannot be empty!");
    }
 
    // (O-03) CHANGED: overrides Mode.loop() with a no-arg signature.
    // The original Output.loop() was already no-arg, so this is just adding
    // the @Override annotation to declare conformance to the Mode contract.
    @Override
    public void loop() {
        if (this.timeTilNextOrder == 0) {
            orderList.getNewOrder();
            timeTilNextOrder = IO.rand.nextInt(5) + 6;
        }
 
        displayRestaurantInfo();
 
        long sTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - sTime < 1000) {
            try {
                if (System.in.available() > 0) {
                    IO.scan.nextLine();
                    quitSession();
                    // (O-03) CHANGED: was new KrazyKitchen() inline, now uses
                    // the inherited Mode.returnToMenu() so both subclasses go
                    // through the same exit path.
                    returnToMenu();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        this.orderList.updateOrderList();
        timeTilNextOrder--;
 
        loop();
    }
 
    // (O-03) RENAMED: was quit(). Renamed to quitSession() to make it clear
    // this only prints the final score, the "return to main menu" step is
    // now handled separately by returnToMenu() inherited from Mode.
    // The original quit() conflated two responsibilities (show score AND
    // jump back to menu) which is why Input had a different method name
    // for the same concept. Splitting them removes that asymmetry.
    public void quitSession() {
        IO.cls();
        ScoreRepository scoreRepository = new ScoreRepository();
        int score = scoreRepository.readScore();
        Table.displayTable("Result", new ArrayList<TableColumn>(Arrays.asList(
            new TableColumn("Your Final Score: " + score, 46)
        )), null);
        IO.enter();
    }
 
    public void displayRestaurantInfo() {
        IO.cls();
        System.out.println();
        System.out.println(Color.id(208) + name + "'s Restaurant" + Color.id(255));
 
        this.orderList.displayOrderList();
        System.out.println();
        this.inventory.displayInventory();
        System.out.println();
        displayGameStats();
        System.out.println();
        Table.displayTable("", new ArrayList<TableColumn>(Arrays.asList(
            new TableColumn("Select an order to process or press enter to return to menu", 60)
        )), null);
    }
 
    public void displayGameStats() {
        ScoreRepository scoreRepository = new ScoreRepository();
        int score = scoreRepository.readScore();
        Table.displayTable("Game Stats", new ArrayList<TableColumn>(Arrays.asList(
            new TableColumn("Current Score: $" + score, 46)
        )), null);
    }
 
}
