package owl.humanresourcesmanagement.enums.user;

public enum EUserState {
    PENDING("Beklemede"),
    ACTIVE("Aktif"),
    INACTIVE("Pasif"),
    BANNED("Erişim Engellendi"),
    DENIED("Reddedildi"),
    DELETED("Silindi");
    //deleted olanlar gözükmesin
    private final String description;
    
    EUserState(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
