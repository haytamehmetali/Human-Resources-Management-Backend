package owl.humanresourcesmanagement.enums.publicHoliday;

public enum EPublicHolidays {
	NATIONAL("Ulusal Tatil"),
	RELIGIOUS("Dini Tatil"),
	OFFICIAL("Resmi Tatil"),
	WEEKEND("Hafta Sonu Tatili"),
	OTHER("Diğer Tatiller");
	
	private final String description;
	
	EPublicHolidays(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
