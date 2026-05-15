package restaurant;

public class Ingredient {

    private String name;
    private int quantity;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public boolean hasEnough(int required) {
        return this.quantity >= required;
    }

}