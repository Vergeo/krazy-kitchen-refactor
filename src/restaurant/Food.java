package restaurant;

import java.util.ArrayList;

public class Food {
	
	private String name;
	private String recipeString;
	private ArrayList<String> ingredients;
	private ArrayList<Integer> quantity;
	
	public Food(String name, String recipeString, ArrayList<String> ingredients, ArrayList<Integer> quantity) {
		super();
		this.name = name;
		this.recipeString = recipeString;
		this.ingredients = ingredients;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecipeString() {
		return recipeString;
	}

	public void setRecipeString(String recipeString) {
		this.recipeString = recipeString;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	public ArrayList<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(ArrayList<Integer> quantity) {
		this.quantity = quantity;
	}
	
}
