package inventory;

import java.util.ArrayList;
import java.util.Arrays;

import utilities.IO;
import utilities.Table;

public class Inventory {
	
	private ArrayList<String> ingredients;
	private ArrayList<Integer> quantity;

	public Inventory() {
		ingredients = new ArrayList<String>();
		quantity = new ArrayList<Integer>();
	}
	
	public void setFreshInventory() {
		ingredients.add("Lettuce");
		ingredients.add("Onions");
		ingredients.add("Bun");
		ingredients.add("Cheese");
		ingredients.add("Tomato");
		ingredients.add("Bacon");
		ingredients.add("Patty");
		ingredients.add("Pickles");
		for (int i = 0; i < this.ingredients.size(); i++) quantity.add(10);
		saveInventoryToFile();
	}
	
	public void saveInventoryToFile() {
		ArrayList<String> lines = new ArrayList<String>();
		for (int i = 0; i < ingredients.size(); i++) {
			String line = ingredients.get(i) + "#" + quantity.get(i) + "\n";
			lines.add(line);
		}
		IO.writeFile("inventory.txt", lines);
	}
	
	public void setInventoryFromFile() {
		ingredients.clear();
		quantity.clear();
		ArrayList<String> data = IO.readFile("inventory.txt");
		for (String line: data) {
			String[] token = line.split("#");
			ingredients.add(token[0]);
			quantity.add(Integer.parseInt(token[1]));
		}
	}
	
	public boolean ifIngredientExist(String ingredient, int qty) {
		int idx = ingredients.indexOf(ingredient);
		return quantity.get(idx) >= qty;
	}
	
	public void useIngredient(String ingredient, int qty) {
		int idx = ingredients.indexOf(ingredient);
		quantity.set(idx, quantity.get(idx) - qty);
		saveInventoryToFile();
	}
	
	public void displayInventory() {
		setInventoryFromFile();
		ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < this.ingredients.size(); i++) {
			ArrayList<String> data = new ArrayList<String>();
			data.add(this.ingredients.get(i));
			data.add(Integer.toString(this.quantity.get(i)));
			datas.add(data);
		}
		Table.displayTable("", new ArrayList<String>(Arrays.asList("Ingredient", "Quantity")), new ArrayList<Integer> (Arrays.asList(15, 10)), datas);
	}
	
	public void restockInventory() {
		setInventoryFromFile();
		for (int i = 0; i < this.ingredients.size(); i++) {
			this.quantity.set(i, this.quantity.get(i) + 10);
		}
		saveInventoryToFile();
	}

}
