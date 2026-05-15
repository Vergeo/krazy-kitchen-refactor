package mode;

import java.util.ArrayList;

import app.KrazyKitchen;
import inventory.Inventory;
import restaurant.Ingredient;
import restaurant.Order;
import restaurant.OrderList;
import utilities.Color;
import utilities.IO;

public class Input {

	public Input() {
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
			else {
				loop("Invalid Process!");
			}
		} catch (Exception e) {
			loop("Invalid Process!");
			return;
		}
	}
		
	public void processOrder(int idx) {
		Inventory inventory = new Inventory();
		inventory.setInventoryFromFile();
		OrderList orderList = new OrderList();
		orderList.setOrderListFromFile();
		idx--;
		if (idx < 0 || idx > orderList.getOrderList().size()) {
			loop("Invalid Order!");
			return;
		}
		
		long startProcessTime = System.currentTimeMillis();
		Order processedOrder = orderList.getOrderList().get(idx);
		long timeLimit = processedOrder.getCustomer().getPatience() * 1000;
		
		System.out.println("");
		System.out.println(Color.id(40) + "Processing "+ processedOrder.getCustomer().getName() +"'s order");
		System.out.println(Color.id(250) + "Ingridients needed: " + processedOrder.getFood().getRecipeString());
		System.out.print(Color.id(255) + ">> " + Color.id(226));
		String in = IO.scan.nextLine();
		long timeElapse = System.currentTimeMillis() - startProcessTime;
		if (!processedOrder.getFood().getRecipeString().equals(in)) {
			loop("Ingrident do not matched!");
			return;
		} else if (timeElapse - 1 > timeLimit) { // -1 for time inconsistency between input and output mode
			loop("This order is expired!");
			return;
		} else {
			OrderList newOrderList = new OrderList();
			newOrderList.setOrderListFromFile();
			int curLimit = (int) ((timeLimit - timeElapse) / 1000);
			for (Order order: newOrderList.getOrderList()) {
				if (order.getCustomer().getName().equals(processedOrder.getCustomer().getName()) &&
						order.getFood().getName().equals(processedOrder.getFood().getName()) &&
						order.getCustomer().getPatience() - 1 <= curLimit &&
						curLimit <= order.getCustomer().getPatience()) {
					inventory.setInventoryFromFile();
					for (Ingredient ing : order.getFood().getIngredients()) {
						if (!inventory.ifIngredientExist(ing.getName(), ing.getQuantity())) {
							loop("Not enough ingredients!");
							return;
						}
					}
					// CHANGED (B-01): iterate Ingredient objects directly instead of syncing two lists by index
					for (Ingredient ing : order.getFood().getIngredients()) {
							inventory.useIngredient(ing.getName(), ing.getQuantity());
					}
					newOrderList.getOrderList().remove(order);
					newOrderList.saveOrderListToFile();
					System.out.println(Color.id(40) + "Order completed! You earned $" + order.getTotalReward());
					ArrayList<String> data = IO.readFile("score.txt");
					int money = Integer.parseInt(data.get(0));
					money += order.getTotalReward();
					data.clear();
					data.add(Integer.toString(money));
					IO.writeFile("score.txt", data);
					loop("");
					return;
				}
			}
			loop("This order is removed or expired!");
			return;
		}
	}
	
	public void restockInventory() {
		ArrayList<String> data = IO.readFile("score.txt");
		int money = Integer.parseInt(data.get(0));
		if (money >= 30) {
			Inventory inventory = new Inventory();
			inventory.restockInventory();
			data.clear();
			data.add(Integer.toString(money-30));
			IO.writeFile("score.txt", data);
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
