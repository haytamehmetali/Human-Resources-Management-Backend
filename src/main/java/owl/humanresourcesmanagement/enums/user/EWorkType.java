package owl.humanresourcesmanagement.enums.user;

public enum EWorkType {
	PART_TIME("Yarı Zamanlı"),
	FULL_TIME("Tam Zamanlı"),
	INTERN("Stajyer"),
	PROJECT_BASED("Proje Bazlı"),
	CONTRACTED("Sözleşmeli");
	
	private final String description;
	
	EWorkType (String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
