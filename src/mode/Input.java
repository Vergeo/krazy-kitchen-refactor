package mode;

import app.KrazyKitchen;
import inventory.Inventory;
import restaurant.Ingredient;
import restaurant.Order;
import restaurant.OrderList;
import utilities.Color;
import utilities.IO;
import utilities.ScoreRepository;

public class Input {

    // (B-04) CHANGED: delegates score file operations to ScoreRepository
    private ScoreRepository scoreRepository;

    public Input() {
        scoreRepository = new ScoreRepository();
        IO.cls();
        loop("");
    }

    public void loop(String err) {
        if (!err.isEmpty()) System.out.println(Color.id(1) + "Error: " + err);
        System.out.println(Color.id(250) + "Enter command (e.g., 'process 1' to process order 1, 'restock' to restock inventory, 'exit' to quit): ");
        System.out.print(Color.id(255) + ">> " + Color.id(226));
        String in = IO.scan.nextLine();
        String[] token = in.split(" ");
        try {
            if (token[0].equals("process")) processOrder(Integer.parseInt(token[1]));
            else if (in.equals("restock")) restockInventory();
            else if (in.equals("exit")) exit();
            else loop("Invalid Process!");
        } catch (Exception e) {
            loop("Invalid Process!");
            return;
        }
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
            loop("This order is expired!");
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

        loop("This order is removed or expired!");
    }

    private boolean validateOrderIndex(int idx, OrderList orderList) {
        if (idx < 0 || idx > orderList.getOrderList().size()) {
            loop("Invalid Order!");
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
            loop("Ingrident do not matched!");
            return false;
        }
        return true;
    }

    private boolean matchesCurrentOrder(Order order, Order processedOrder, int curLimit) {
        return order.getCustomer().getName().equals(processedOrder.getCustomer().getName()) &&
               order.getFood().getName().equals(processedOrder.getFood().getName()) &&
               order.getCustomer().getPatience() - 1 <= curLimit &&
               curLimit <= order.getCustomer().getPatience();
    }

    private void completeOrder(Order order, OrderList newOrderList) {
        Inventory inventory = new Inventory();
        inventory.setInventoryFromFile();

        for (Ingredient ing : order.getFood().getIngredients()) {
            if (!inventory.ifIngredientExist(ing.getName(), ing.getQuantity())) {
                loop("Not enough ingredients!");
                return;
            }
        }

        for (Ingredient ing : order.getFood().getIngredients()) {
            inventory.useIngredient(ing.getName(), ing.getQuantity());
        }

        newOrderList.getOrderList().remove(order);
        newOrderList.saveOrderListToFile();

        System.out.println(Color.id(40) + "Order completed! You earned $" + order.getTotalReward());

        // (B-04) CHANGED: delegates score update to ScoreRepository
        scoreRepository.addScore(order.getTotalReward());

        loop("");
    }

    public void restockInventory() {
        int money = scoreRepository.readScore();
        if (money >= 30) {
            Inventory inventory = new Inventory();
            inventory.restockInventory();
            // (B-04) CHANGED: delegates score update to ScoreRepository
            scoreRepository.subtractScore(30);
            System.out.println(Color.id(40) + "Restock Successful (- $30)");
            loop("");
        } else {
            loop("Not Enough Money!");
        }
    }

    public void exit() {
        new KrazyKitchen();
    }

}