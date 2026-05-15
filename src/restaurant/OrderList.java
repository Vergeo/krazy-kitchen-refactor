package restaurant;

import java.util.ArrayList;
import java.util.Arrays;

import utilities.Table;
import utilities.TableColumn;

// (B-04) CHANGED: OrderList now only manages the order list and business logic
// File I/O responsibility moved to OrderRepository
public class OrderList {

    private FoodList foodList;
    private ArrayList<Order> orderList;
    // (B-04) CHANGED: delegates file operations to OrderRepository
    private OrderRepository repository;

    public OrderList() {
        foodList = new FoodList();
        orderList = new ArrayList<Order>();
        repository = new OrderRepository();
    }

    public void setOrderListFromFile() {
        // (B-04) CHANGED: delegates to repository instead of doing I/O inline
        orderList = repository.loadFromFile();
    }

    public void saveOrderListToFile() {
        // (B-04) CHANGED: delegates to repository instead of doing I/O inline
        repository.saveToFile(orderList);
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void getNewOrder() {
        boolean vip = utilities.IO.rand.nextBoolean();
        customer.Customer customer;
        if (vip) customer = new customer.VIPCustomer();
        else customer = new customer.CasualCustomer();
        Food food = this.foodList.getRandomFood();
        orderList.add(new Order(customer, food));
        saveOrderListToFile();
    }

    public void updateOrderList() {
        setOrderListFromFile();
        ArrayList<Order> newOrderList = new ArrayList<Order>();
        for (Order order : orderList) {
            order.getCustomer().decreasePatience();
            if (order.getCustomer().getPatience() >= 0) newOrderList.add(order);
        }
        this.orderList = newOrderList;
        saveOrderListToFile();
    }

    public void displayOrderList() {
        setOrderListFromFile();
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
        int number = 1;
        for (Order order : orderList) {
            ArrayList<String> data = new ArrayList<String>();
            data.add(Integer.toString(number));
            data.add(order.getCustomer().getName());
            data.add(order.getFood().getName());
            data.add("$" + Integer.toString(order.getTotalReward()));
            data.add(Integer.toString(order.getCustomer().getPatience()) + "s");
            datas.add(data);
            number++;
        }
        Table.displayTable("", new ArrayList<TableColumn>(Arrays.asList(
            new TableColumn("No.", 5),
            new TableColumn("Customer", 15),
            new TableColumn("Order", 20),
            new TableColumn("Reward", 6),
            new TableColumn("Time", 5)
        )), datas);
    }

}