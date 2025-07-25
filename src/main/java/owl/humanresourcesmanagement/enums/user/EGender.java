package owl.humanresourcesmanagement.enums.user;

public enum EGender {
    MALE("Erkek"),
    FEMALE("Kadın"),
    UNDEFINED("Belirtmek istemiyorum");
    
    private final String description;
    
    EGender(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
