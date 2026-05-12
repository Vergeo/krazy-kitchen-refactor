package customer;

import utilities.IO;

public abstract class Customer {
	
	private String name;
	private Integer patience;

	public Customer(String name, Integer patience) {
		super();
		this.name = name;
		this.patience = patience;
	}
	
	public Customer() {
		this.name = NameList.getRandomName();
		this.patience = 1 + IO.rand.nextInt(20) + 15;
	}

	public String getName() {
		return name;
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
	
}
