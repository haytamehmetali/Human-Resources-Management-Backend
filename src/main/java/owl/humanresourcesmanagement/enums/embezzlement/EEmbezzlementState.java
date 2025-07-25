package owl.humanresourcesmanagement.enums.embezzlement;

public enum EEmbezzlementState {
    APPROVED("Onaylandı / Zimmete Atandı"),
    OTHER("Diğer Tüm Durumlar"),
    REJECTED("Reddedildi"),
    PENDING("Beklemede");

    //    LOST("Kayıp"),
    //    DAMAGED("Hasarlı"),
    //    RETURNED("İade edildi"),
    //   UNDER_MAINTENANCE("Bakımda"),
    //  REJECTED("Reddedildi");

    private final String description;

    EEmbezzlementState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
