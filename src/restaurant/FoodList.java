package restaurant;

import java.util.ArrayList;
import java.util.Arrays;

import utilities.IO;

public class FoodList {
	
	private ArrayList<Food> foodList;

	public FoodList() {
		foodList = new ArrayList<Food>();
		fillFoodList();
	}
	
	private void fillFoodList() {
		Food cheeseBurger = new Food(
				"Cheese Burger",
				"Bun, Cheese, Patty, Bun",
    new ArrayList<Ingredient>(Arrays.asList(
        new Ingredient("Bun", 2),
        new Ingredient("Cheese", 1),
        new Ingredient("Patty", 1)
    )));
		foodList.add(cheeseBurger);
		
		Food baconBurger = new Food(
				"Bacon Burger",
				"Bun, Bacon, Patty, Lettucee, Tomato, Bun",
    new ArrayList<Ingredient>(Arrays.asList(
        new Ingredient("Bun", 2),
        new Ingredient("Bacon", 1),
        new Ingredient("Patty", 1),
        new Ingredient("Lettuce", 1),
        new Ingredient("Tomato", 1)
    )));
		foodList.add(baconBurger);
		
		Food veggieBurger = new Food(
				"Veggie Burger",
				"Bun, Lettuce, Tomato, Onions, Pickels, Bun",
    new ArrayList<Ingredient>(Arrays.asList(
        new Ingredient("Bun", 2),
        new Ingredient("Lettuce", 1),
        new Ingredient("Tomato", 1),
        new Ingredient("Onions", 1),
        new Ingredient("Pickles", 1)
    )));
		foodList.add(veggieBurger);
		
		Food deluxeBurger = new Food(
				"Deluxe Burger",
				"Bun, Cheese, Bacon, Patty, Lettuce, Tomato, Onions, Bun",
    new ArrayList<Ingredient>(Arrays.asList(
        new Ingredient("Bun", 2),
        new Ingredient("Cheese", 1),
        new Ingredient("Bacon", 1),
        new Ingredient("Patty", 1),
        new Ingredient("Lettuce", 1),
        new Ingredient("Tomato", 1),
        new Ingredient("Onions", 1)
    )));
		foodList.add(deluxeBurger);
	}
	
	public Food getRandomFood() {
		int idx = IO.rand.nextInt(foodList.size());
		return foodList.get(idx);
	}
	
	public Food getFood(String name) {
		for (Food food : foodList) {
			if (food.getName().equals(name)) return food;
		}
		return null;
	}
}
