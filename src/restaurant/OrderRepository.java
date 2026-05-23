package restaurant;

import java.util.ArrayList;

import customer.Customer;
import customer.CustomerType;
import utilities.IO;

// (B-04) EXTRACTED from OrderList: solely responsible for reading and writing orders to file
public class OrderRepository {

    private FoodList foodList;

    public OrderRepository() {
        foodList = new FoodList();
    }

    // (B-04) EXTRACTED from OrderList.setOrderListFromFile()
    public ArrayList<Order> loadFromFile() {
        ArrayList<Order> orders = new ArrayList<Order>();
        ArrayList<String> data = IO.readFile("restaurant.txt");
        for (String line : data) {
            String[] token = line.split("#");
            CustomerType type = token[1].contains("VIP") ? CustomerType.VIP : CustomerType.Casual;
            int patience = Integer.parseInt(token[4]);
            Customer customer = new Customer(token[1], patience, type);
            Food food = foodList.getFood(token[2]);
            orders.add(new Order(customer, food));
        }
        return orders;
    }

    // (B-04) EXTRACTED from OrderList.saveOrderListToFile()
    public void saveToFile(ArrayList<Order> orderList) {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            String line = (i + 1) + "#" +
                    order.getCustomer().getName() + "#" +
                    order.getFood().getName() + "#" +
                    order.getTotalReward() + "#" +
                    order.getCustomer().getPatience() + "\n";
            lines.add(line);
        }
        IO.writeFile("restaurant.txt", lines);
    }

}