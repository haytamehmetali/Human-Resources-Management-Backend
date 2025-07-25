package owl.humanresourcesmanagement.enums.spendings;

public enum ESpendingType {
    ACCOMMODATION("Konaklama"),
    MEAL("Yemek"),
    TRANSPORTATION("Ulaşım"),
    OFFICE_SUPPLIES("Ofis Malzemeleri"),
    SOFTWARE("Yazılım Lisans/Abonelik"),
    CLIENT_ENTERTAINMENT("Müşteri Ağırlama"),
    TRAINING("Eğitim"),
    OTHER("Diğer");

    private final String description;

    ESpendingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
