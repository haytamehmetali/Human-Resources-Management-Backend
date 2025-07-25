package owl.humanresourcesmanagement.enums.user;

public enum EEmploymentStatus {
    EMPLOYED("Çalışıyor"),
    RETIRED("Emekli"),
    TERMINATED("İşten çıkarıldı"),
    RESIGNED("İstifa etti"),
    ON_LEAVE("İzinli"),
    UNDEFINED("Tanımsız");
    
    // service kısmında eklemeler olabilir (daha detaylı göstermek)
    // boolean ile aktif çalışanlar gösterilebilir enumda da yapılabilir
    
    private final String description;
    
    EEmploymentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
