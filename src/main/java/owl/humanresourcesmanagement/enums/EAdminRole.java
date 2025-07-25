package owl.humanresourcesmanagement.enums;

public enum EAdminRole {
    ADMIN("Yönetici"),
    SUPER_ADMIN("Kurucu/Baş Yönetici");
    
    private final String description;
    
    EAdminRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
