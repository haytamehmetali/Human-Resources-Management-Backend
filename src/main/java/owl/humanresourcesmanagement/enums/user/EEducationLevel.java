package owl.humanresourcesmanagement.enums.user;

public enum EEducationLevel {
	NONE("Yok"),
	PRIMARY_SCHOOL("İlkokul/Ortaokul"),
	HIGH_SCHOOL("Lise"),
	ASSOCIATE_DEGREE("Ön Lisans"),
	BACHELOR_DEGREE("Lisans"),
	MASTER_DEGREE("Yüksek Lisans"),
	DOCTORATE_DEGREE("Doktora");
	
	private final String description;
	
	EEducationLevel(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
