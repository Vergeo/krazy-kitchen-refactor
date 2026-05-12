package restaurant;

import customer.Customer;
import customer.VIPCustomer;

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
		for (int qty : this.food.getQuantity()) {
			totalIngredient += qty;
		}
		int baseReward = 5 + totalIngredient*2;
		if (customer instanceof VIPCustomer) {
			VIPCustomer vip = (VIPCustomer) customer;
			return baseReward + vip.getBonusReward();
		} else return baseReward;
	}

}
