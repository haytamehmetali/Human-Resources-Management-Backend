package owl.humanresourcesmanagement.enums.company;

public enum ERegion {
    TURKEY("Türkiye"),
    GERMANY("Almanya"),
    FRANCE("Fransa"),
    UNITED_KINGDOM("Birleşik Krallık"),
    UNITED_STATES("Amerika Birleşik Devletleri"),
    CANADA("Kanada"),
    JAPAN("Japonya"),
    CHINA("Çin"),
    BRAZIL("Brezilya"),
    AUSTRALIA("Avustralya"),
    ITALY("İtalya"),
    SPAIN("İspanya"),
    INDIA("Hindistan"),
    RUSSIA("Rusya"),
    SOUTH_KOREA("Güney Kore"),
    MEXICO("Meksika"),
    NETHERLANDS("Hollanda"),
    SWITZERLAND("İsviçre"),
    SWEDEN("İsveç"),
    NORWAY("Norveç"),
    ARGENTINA("Arjantin"),
    POLAND("Polonya"),
    BELGIUM("Belçika"),
    AUSTRIA("Avusturya"),
    DENMARK("Danimarka"),
    FINLAND("Finlandiya"),
    PORTUGAL("Portekiz"),
    IRELAND("İrlanda"),
    GREECE("Yunanistan"),
    NEW_ZEALAND("Yeni Zelanda"),
    SOUTH_AFRICA("Güney Afrika"),
    SAUDI_ARABIA("Suudi Arabistan"),
    UNITED_ARAB_EMIRATES("Birleşik Arap Emirlikleri"),
    SINGAPORE("Singapur"),
    THAILAND("Tayland"),
    MALAYSIA("Malezya"),
    INDONESIA("Endonezya"),
    VIETNAM("Vietnam"),
    PHILIPPINES("Filipinler"),
    EGYPT("Mısır"),
    PAKISTAN("Pakistan"),
    UKRAINE("Ukrayna"),
    HUNGARY("Macaristan"),
    ROMANIA("Romanya"),
    CZECH_REPUBLIC("Çekya"),
    SLOVAKIA("Slovakya"),
    BULGARIA("Bulgaristan"),
    SERBIA("Sırbistan"),
    CROATIA("Hırvatistan"),
    MOROCCO("Fas"),
    TUNISIA("Tunus"),
    ALGERIA("Cezayir"),
    KAZAKHSTAN("Kazakistan"),
    QATAR("Katar"),
    KUWAIT("Kuveyt");
    
    private final String description;
    
    ERegion(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
