package owl.humanresourcesmanagement.enums;

public enum EState {
    PASSIVE("Pasif"),
    ACTIVE("Aktif");
    
    private final String description;
    
    EState(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
