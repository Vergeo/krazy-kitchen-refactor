package mode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import app.KrazyKitchen;
import inventory.Inventory;
import restaurant.OrderList;
import utilities.Color;
import utilities.IO;
import utilities.Table;

public class Output {
	
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
		if (!err.isEmpty()) System.out.println(Color.id(1) + "Error: " + err);
		System.out.print(Color.id(255) + "Please enter your name (Cannot be empty): " + Color.id(226));
		this.name = IO.scan.nextLine();
		if (this.name.isEmpty()) askForName("Name cannot be empty!");
	}
	
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
					quit();
					new KrazyKitchen();
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
	
	public void quit() {
		IO.cls();
		ArrayList<String> data = IO.readFile("score.txt");
		int score = Integer.parseInt(data.get(0));
		Table.displayTable("Result", new ArrayList<String>(Arrays.asList("Your Final Score: " + score)), new ArrayList<Integer> (Arrays.asList(46)), null);
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
		Table.displayTable("", new ArrayList<String>(Arrays.asList("Select an order to process or press enter to return to menu")), new ArrayList<Integer> (Arrays.asList(60)), null);
	}
	
	public void displayGameStats() {
		ArrayList<String> data = IO.readFile("score.txt");
		int score = Integer.parseInt(data.get(0));
		Table.displayTable("Game Stats", new ArrayList<String>(Arrays.asList("Current Score: $" + score)), new ArrayList<Integer> (Arrays.asList(46)), null);
	}

}
