package customer;

public class VIPCustomer extends Customer{

	private int bonusReward;
	
	public VIPCustomer() {
		super();
		this.bonusReward = 10;
		this.setName(this.getName() + " VIP");
	}

	public VIPCustomer(String name, Integer patience, int bonusReward) {
		super(name, patience);
		this.bonusReward = bonusReward;
	}

	public void setBonusReward(int bonusReward) {
		this.bonusReward = bonusReward;
	}

	@Override
	public int getBonusReward() { return this.bonusReward; }

}
