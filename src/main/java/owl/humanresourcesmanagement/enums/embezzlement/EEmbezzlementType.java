package owl.humanresourcesmanagement.enums.embezzlement;

public enum EEmbezzlementType {
    ELECTRONIC_DEVICES("Elektronik Cihazlar"),
    OFFICE_EQUIPMENT("Ofis Ekipmanları");
    // VEHICLES_AND_TRANSPORTATION("Araçlar ve Ulaşım"),
    // PROTECTIVE_GEAR("Koruyucu Ekipmanlar"),
    // TECHNICAL_EQUIPMENT("Teknik Donanımlar"),
    // STATIONERY_AND_OFFICE_SUPPLIES("Kırtasiye ve Ofis Malzemeleri"),
    // COMPANY_CARDS("Şirket Kartları"),
    // CLOTHING_AND_UNIFORMS("Kıyafet ve Üniformalar");

    private final String description;

    EEmbezzlementType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
