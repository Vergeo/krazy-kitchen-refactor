package inventory;

import java.util.ArrayList;
import java.util.Arrays;

import restaurant.Ingredient;
import utilities.IO;
import utilities.Table;
import utilities.TableColumn;

public class Inventory {

    // CHANGED (B-01): two parallel lists replaced by a single list of Ingredient objects
    private ArrayList<Ingredient> ingredients;

    public Inventory() {
        ingredients = new ArrayList<Ingredient>();
    }

    public void setFreshInventory() {
        ingredients.clear();
        // CHANGED (B-01): each entry is now a self-contained Ingredient object
        ingredients.addAll(Arrays.asList(
            new Ingredient("Lettuce", 10),
            new Ingredient("Onions",  10),
            new Ingredient("Bun",     10),
            new Ingredient("Cheese",  10),
            new Ingredient("Tomato",  10),
            new Ingredient("Bacon",   10),
            new Ingredient("Patty",   10),
            new Ingredient("Pickles", 10)
        ));
        saveInventoryToFile();
    }

    public void saveInventoryToFile() {
        ArrayList<String> lines = new ArrayList<String>();
        for (Ingredient ing : ingredients) {
            lines.add(ing.getName() + "#" + ing.getQuantity() + "\n");
        }
        IO.writeFile("inventory.txt", lines);
    }

    public void setInventoryFromFile() {
        ingredients.clear();
        ArrayList<String> data = IO.readFile("inventory.txt");
        for (String line : data) {
            String[] token = line.split("#");
            ingredients.add(new Ingredient(token[0], Integer.parseInt(token[1])));
        }
    }

    // CHANGED (B-01): no longer needs two params to sync by index and finds Ingredient by name directly
    public boolean ifIngredientExist(String name, int required) {
        for (Ingredient ing : ingredients) {
            if (ing.getName().equals(name)) return ing.hasEnough(required);
        }
        return false;
    }

    // CHANGED (B-01): delegates decrease to Ingredient's own method
    public void useIngredient(String name, int amount) {
        for (Ingredient ing : ingredients) {
            if (ing.getName().equals(name)) {
                ing.decreaseQuantity(amount);
                break;
            }
        }
        saveInventoryToFile();
    }

    public void displayInventory() {
        setInventoryFromFile();
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
        for (Ingredient ing : ingredients) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(ing.getName());
            row.add(Integer.toString(ing.getQuantity()));
            datas.add(row);
        }
			Table.displayTable("", new ArrayList<TableColumn>(Arrays.asList(
					new TableColumn("Ingredient", 15),
					new TableColumn("Quantity", 10)
			)), datas);
    }

    public void restockInventory() {
        setInventoryFromFile();
        // CHANGED (B-01): delegates increase to Ingredient's own method
        for (Ingredient ing : ingredients) {
            ing.increaseQuantity(10);
        }
        saveInventoryToFile();
    }

}