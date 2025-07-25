package owl.humanresourcesmanagement.enums.permissions;

public enum EPermissionState {
    PENDING("Bekliyor"),
    APPROVED("Onaylandı"),
    REJECTED("Reddedildi");

    private final String description;

    EPermissionState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
