package owl.humanresourcesmanagement.enums.spendings;

public enum ESpendingState {
    PENDING("Onay Bekliyor"),
    REJECTED("Reddedildi"),
    APPROVED("Onaylandı");

    private final String description;

    ESpendingState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
