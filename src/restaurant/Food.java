package restaurant;

import java.util.ArrayList;

public class Food {

    private String name;
    private String recipeString;
    // CHANGED (B-01): two parallel lists replaced by a single list of Ingredient objects
    private ArrayList<Ingredient> ingredients;

    public Food(String name, String recipeString, ArrayList<Ingredient> ingredients) {
        super();
        this.name = name;
        this.recipeString = recipeString;
        this.ingredients = ingredients;
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}