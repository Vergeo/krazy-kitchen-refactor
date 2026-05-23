package customer;

import utilities.IO;

public class Customer {
	
	private String name;
	private Integer patience;
	// (D-01) NEW: Add CustomerType to differentiate Casual and VIP Customer
	private CustomerType type;

	public Customer(String name, Integer patience, CustomerType type) {
		super();
		this.name = name;
		this.patience = patience;
		this.type = type;
	}
	
	public Customer(CustomerType type) {
		this.patience = 1 + IO.rand.nextInt(20) + 15;
		this.type = type;
		this.name = NameList.getRandomName() + (this.type == CustomerType.VIP ? " VIP" : "");
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPatience() {
		return patience;
	}

	public void setPatience(Integer patience) {
		this.patience = patience;
	}
	
	public void decreasePatience() {
		this.patience--;
	}
	
	public int getBonusReward() { 
		return (type == CustomerType.VIP) ? 10 : 0;
	}
}
