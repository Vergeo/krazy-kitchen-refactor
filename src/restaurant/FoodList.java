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
				new ArrayList<String>(Arrays.asList("Bun", "Cheese", "Patty")),
				new ArrayList<Integer>(Arrays.asList(2, 1, 1)));
		foodList.add(cheeseBurger);
		
		Food baconBurger = new Food(
				"Bacon Burger",
				"Bun, Bacon, Patty, Lettucee, Tomato, Bun",
				new ArrayList<String>(Arrays.asList("Bun", "Bacon", "Patty", "Lettuce", "Tomato")),
				new ArrayList<Integer>(Arrays.asList(2, 1, 1, 1, 1)));
		foodList.add(baconBurger);
		
		Food veggieBurger = new Food(
				"Veggie Burger",
				"Bun, Lettuce, Tomato, Onions, Pickels, Bun",
				new ArrayList<String>(Arrays.asList("Bun", "Lettuce", "Tomato", "Onions", "Pickels")),
				new ArrayList<Integer>(Arrays.asList(2, 1, 1, 1, 1)));
		foodList.add(veggieBurger);
		
		Food deluxeBurger = new Food(
				"Deluxe Burger",
				"Bun, Cheese, Bacon, Patty, Lettuce, Tomato, Onions, Bun",
				new ArrayList<String>(Arrays.asList("Bun", "Cheese", "Bacon", "Patty", "Lettuce", "Tomato", "Onions")),
				new ArrayList<Integer>(Arrays.asList(2, 1, 1, 1, 1, 1, 1)));
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
