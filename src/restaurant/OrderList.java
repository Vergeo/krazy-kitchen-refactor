package restaurant;

import java.util.ArrayList;
import java.util.Arrays;

import customer.CasualCustomer;
import customer.Customer;
import customer.VIPCustomer;
import utilities.IO;
import utilities.Table;
import utilities.TableColumn;

public class OrderList {
	
	private FoodList foodList;
	private ArrayList<Order> orderList;

	public OrderList() {
		foodList = new FoodList();
		orderList = new ArrayList<Order>();
	}
	
	public void setOrderListFromFile() {
		orderList.clear();
		ArrayList<String> data = IO.readFile("restaurant.txt");
		for (String line: data) {
			String[] token = line.split("#");
			Customer customer;
			if (token[1].endsWith("VIP"))
				customer = new VIPCustomer(token[1], Integer.parseInt(token[4]), 10);
			else
				customer = new CasualCustomer(token[1], Integer.parseInt(token[4]));

			Food food = this.foodList.getFood(token[2]);
			orderList.add(new Order(customer, food));
		}
	}
	
	public void saveOrderListToFile() {
		ArrayList<String> lines = new ArrayList<String>();
		for (int i = 0; i < orderList.size(); i++) {
			Order order = orderList.get(i);
			String line = (i+1) + "#" +
					order.getCustomer().getName() + "#" +
					order.getFood().getName() + "#" +
					order.getTotalReward() + "#" +
					order.getCustomer().getPatience() + "\n";
			lines.add(line);
		}
		IO.writeFile("restaurant.txt", lines);
	}
	
	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void getNewOrder() {
		boolean vip = IO.rand.nextBoolean();
		Customer customer;
		if (vip) customer = new VIPCustomer();
		else customer = new CasualCustomer();
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
		for (Order order: orderList) {
			ArrayList<String> data = new ArrayList<String> ();
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
