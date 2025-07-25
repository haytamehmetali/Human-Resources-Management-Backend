package owl.humanresourcesmanagement.enums.company;

public enum ECompanyType {
    TECHNOLOGY("Teknoloji"),
    FOOD("Gıda"),
    HEALTHCARE("Sağlık"),
    EDUCATION("Eğitim"),
    CONSTRUCTION("İnşaat"),
    FINANCE("Finans"),
    ENERGY("Enerji"),
    TOURISM("Turizm"),
    MEDIA_AND_ENTERTAINMENT("Medya ve Eğlence"),
    AUTOMOTIVE("Otomotiv"),
    FASHION_AND_TEXTILE("Moda ve Tekstil"),
    RETAIL("Perakende"),
    TELECOMMUNICATION("Telekomünikasyon"),
    AGRICULTURE("Tarım"),
    LOGISTICS("Lojistik"),
    REAL_ESTATE("Emlak"),
    LEGAL("Hukuk"),
    CONSULTING("Danışmanlık"),
    SOFTWARE_AS_A_SERVICE("Yazılım Hizmeti (SaaS)"),
    ECOMMERCE("E-Ticaret"),
    PHARMACEUTICAL("İlaç"),
    TRANSPORTATION("Ulaşım"),
    SECURITY("Güvenlik"),
    ART_AND_DESIGN("Sanat ve Tasarım"),
    MANUFACTURING("Üretim"),
    MINING("Madencilik"),
    ENVIRONMENTAL("Çevre"),
    SPORTS_AND_RECREATION("Spor ve Eğlence"),
    GOVERNMENT("Kamu"),
    NON_PROFIT("Kâr Amacı Gütmeyen"),
    UNKNOWN("Bilinmeyen");
    
    private final String description;
    
    ECompanyType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
