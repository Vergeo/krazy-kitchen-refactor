package mode;

import inventory.Inventory;
import restaurant.Order;
import restaurant.OrderList;
import utilities.Color;
import utilities.IO;
import utilities.ScoreRepository;

// (O-03) CHANGED: Input now extends Mode and conforms to the shared interface.
public class Input extends Mode {

    // (B-04) CHANGED: delegates score file operations to ScoreRepository
    private ScoreRepository scoreRepository;

    // (O-03) CHANGED: store the latest error message as a field so the public
    // loop() signature matches the Mode contract. Previously loop took a
    // String err parameter, which broke uniformity with Output.loop().
    private String pendingError;
    
    // (D-03): EXTRACTED: single instantiation instead of multiple across methods
    Inventory inventory = new Inventory();

    public Input() {
        scoreRepository = new ScoreRepository();
        pendingError = "";
        IO.cls();
        loop();
    }

    // (O-03) CHANGED: overrides Mode.loop() with a no-arg signature.
    // Error state is read from the pendingError field, set by callers via
    // loopWithError() when they need to redisplay the prompt with a message.
    @Override
    public void loop() {
        printError(pendingError);
        pendingError = "";

        System.out.println(Color.id(250) + "Enter command (e.g., 'process 1' to process order 1, 'restock' to restock inventory, 'exit' to quit): ");
        System.out.print(Color.id(255) + ">> " + Color.id(226));
        String in = IO.scan.nextLine();
        String[] token = in.split(" ");
        try {
            if (token[0].equals("process")) processOrder(Integer.parseInt(token[1]));
            else if (in.equals("restock")) restockInventory();
            else if (in.equals("exit")) returnToMenu();
            else loopWithError("Invalid Process!");
        } catch (Exception e) {
            loopWithError("Invalid Process!");
            return;
        }
    }

    // (O-03) NEW: internal helper that preserves the original "loop with an
    // error message" behavior without polluting the public Mode interface.
    private void loopWithError(String err) {
        this.pendingError = err;
        loop();
    }

    public void processOrder(int idx) {
        OrderList orderList = new OrderList();
        orderList.setOrderListFromFile();
        idx--;

        if (!validateOrderIndex(idx, orderList)) return;

        Order processedOrder = orderList.getOrderList().get(idx);
        long timeLimit = processedOrder.getCustomer().getPatience() * 1000;
        long startProcessTime = System.currentTimeMillis();

        if (!getPlayerRecipeInput(processedOrder)) return;

        long timeElapse = System.currentTimeMillis() - startProcessTime;

        if (timeElapse - 1 > timeLimit) {
            loopWithError("This order is expired!");
            return;
        }

        OrderList newOrderList = new OrderList();
        newOrderList.setOrderListFromFile();
        int curLimit = (int) ((timeLimit - timeElapse) / 1000);

        for (Order order : newOrderList.getOrderList()) {
            if (matchesCurrentOrder(order, processedOrder, curLimit)) {
                completeOrder(order, newOrderList);
                return;
            }
        }

        loopWithError("This order is removed or expired!");
    }

    private boolean validateOrderIndex(int idx, OrderList orderList) {
        if (idx < 0 || idx > orderList.getOrderList().size()) {
            loopWithError("Invalid Order!");
            return false;
        }
        return true;
    }

    private boolean getPlayerRecipeInput(Order processedOrder) {
        System.out.println("");
        System.out.println(Color.id(40) + "Processing " + processedOrder.getCustomer().getName() + "'s order");
        System.out.println(Color.id(250) + "Ingridients needed: " + processedOrder.getFood().getRecipeString());
        System.out.print(Color.id(255) + ">> " + Color.id(226));
        String in = IO.scan.nextLine();
        if (!processedOrder.getFood().getRecipeString().equals(in)) {
            loopWithError("Ingrident do not matched!");
            return false;
        }
        return true;
    }

    private boolean matchesCurrentOrder(Order order, Order processedOrder, int curLimit) {
        //(C-01) NEW: extract the message chain and move it into a variable
        var customerName = order.getCustomer().getName();
        var foodName = order.getFood().getName();
        var processedCustomer = processedOrder.getCustomer().getName();
        var processedFood = processedOrder.getFood().getName();

        return customerName.equals(processedCustomer) &&
               foodName.equals(processedFood) &&
               order.getCustomer().getPatience() - 1 <= curLimit &&
               curLimit <= order.getCustomer().getPatience();
    }

    private void completeOrder(Order order, OrderList newOrderList) {
    	// (D-03): EXTRACTED: move inventory into attribute
        inventory.setInventoryFromFile();
        
        //CHANGED (C-02): For loop to validate Ingredient stock is extracted and moved to Inventory. 
        if (!inventory.consumeIngredientsFor(order.getFood())) {
            loopWithError("Not enough ingredients!");
            return;
        }

        newOrderList.getOrderList().remove(order);
        newOrderList.saveOrderListToFile();

        // (TCP-02) CHANGED: uses scoreRepository.formatScore() instead of hardcoded currency symbol
        System.out.println(Color.id(40) + "Order completed! You earned " + scoreRepository.formatScore(order.getTotalReward()));

        // (B-04) CHANGED: delegates score update to ScoreRepository
        scoreRepository.addScore(order.getTotalReward());

        loop();
    }

    public void restockInventory() {
        int money = scoreRepository.readScore();
        if (money >= 30) {
        	// (D-03): EXTRACTED: move inventory into attribute
            inventory.restockInventory();
            // (B-04) CHANGED: delegates score update to ScoreRepository
            scoreRepository.subtractScore(30);
            // (TCP-02) CHANGED: uses scoreRepository.formatScore() instead of hardcoded currency symbol
            System.out.println(Color.id(40) + "Restock Successful (- " + scoreRepository.formatScore(30) + ")");
            loop();
        } else {
            loopWithError("Not Enough Money!");
        }
    }

    // (O-03) REMOVED: exit() was renamed and pulled up to Mode.returnToMenu().
    // The old "exit" command in loop() now calls returnToMenu() directly.

}