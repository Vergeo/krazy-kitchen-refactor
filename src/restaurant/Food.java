package restaurant;

import java.util.ArrayList;

public class Food {

    private String name;
    // (TCP-01 ) CHANGED: recipeString - call it automatically from ingredients
    // CHANGED (B-01): two parallel lists replaced by a single list of Ingredient objects
    private ArrayList<Ingredient> ingredients;

    public Food(String name, ArrayList<Ingredient> ingredients) {
        super();
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // (TCP-01) CHANGED: recipeString is now called from ingredients automatically
    public String getRecipeString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < ingredients.size(); i++){
            sb.append(ingredients.get(i).getName());
            if(i < ingredients.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}