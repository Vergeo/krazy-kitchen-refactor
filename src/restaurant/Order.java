package restaurant;
import customer.Customer;

public class Order {
	
	private Customer customer;
	private Food food;

	public Order(Customer customer, Food food) {
		this.customer = customer;
		this.food = food;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
public int getTotalReward() {
    int totalIngredient = 0;
    // CHANGED (B-01): iterate Ingredient objects directly, quantity is inside each one
    for (Ingredient ing : this.food.getIngredients()) {
        totalIngredient += ing.getQuantity();
    }
    return 5 + totalIngredient * 2 + customer.getBonusReward(); // CHANGED (O-01): remove instance of
}

}
