package inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import restaurant.Food;
import restaurant.FoodList;
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

    // (TCP-01) CHANGED: ingredients are now auto filled from FoodList, adding new food no longer need to modify this class
    public void setFreshInventory() {
        ingredients.clear();
        // CHANGED (B-01): each entry is now a self-contained Ingredient object
        

        Set<String> seen = new HashSet<>();
        FoodList foodList = new FoodList();

        for(Food food : foodList.getAllFoods()) {
            for(Ingredients ing : food.getIngredients()) {
                if(!seen.contains(ing.getName())) {
                    seen.add(ing.getName());
                    ingredients.add(new Ingredients(ing.getName(), 10));
                }
            }
        }
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

    //NEW (C-02): extracted validation for stock Ingredient
    public boolean consumeIngredientsFor(Food food) {
        for (Ingredient ing : food.getIngredients()) {
            if (!ifIngredientExist(ing.getName(), ing.getQuantity())) {
                return false;
            }
        }

        for (Ingredient ing : food.getIngredients()) {
            useIngredient(ing.getName(), ing.getQuantity());
        }
 
        return true;
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