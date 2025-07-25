package owl.humanresourcesmanagement.enums.company;

public enum ECompanyState {
    PENDING("Beklemede"),
    ACCEPTED("Kabul Edildi"),
    DELETED("Silindi"),
    DENIED("Reddedildi");
    
    private final String description;
    
    ECompanyState(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
