package owl.humanresourcesmanagement.enums.company;

public enum ECompanyMembershipType {
	NONE("Üyelik YAPILMADI -TEST", 0.0, 0),
	
	// Monthly Membership
	MONTHLY_BASIC("Temel Üyelik", 9.99, 2),
	MONTHLY_PREMIUM("Premium Üyelik", 29.99, 4),
	MONTHLY_ENTERPRISE("Kurumsal Üyelik", 39.99, 5),
	
	// Yearly Membership
	YEARLY_BASIC("Temel Üyelik", 99.99, 2),
	YEARLY_PREMIUM("Premium Üyelik", 299.99, 4),
	YEARLY_ENTERPRISE("Kurumsal Üyelik", 399.99, 5);
	
	private final String description;
	private final double fee;
	private final int maxUsers;
	
	ECompanyMembershipType(String description, double fee, int maxUsers) {
		this.description = description;
		this.fee = fee;
		this.maxUsers = maxUsers;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getFee() {
		return fee;
	}
	
	public int getMaxUsers() {
		return maxUsers;
	}
	
}
