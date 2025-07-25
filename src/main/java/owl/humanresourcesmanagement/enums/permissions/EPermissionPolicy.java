package owl.humanresourcesmanagement.enums.permissions;

import java.time.LocalDate;
import java.time.Period;

public enum EPermissionPolicy {
    LESS_THAN_ONE_YEAR(10, "0-1 yıl arası çalışma süresi için 10 gün izin hakkı."),
    BETWEEN_ONE_AND_TEN_YEARS(20, "1-10 yıl arası çalışma süresi için 20 gün izin hakkı."),
    MORE_THAN_TEN_YEARS(30, "10 yıldan fazla çalışma süresi için 30 gün izin hakkı.");

    private final int days;
    private final String description;

    EPermissionPolicy(int days, String description) {
        this.days = days;
        this.description = description;
    }

    public int getDays() {
        return days;
    }

    public String getDescription() {
        return description;
    }

    //YILLIK İZİN GENEL DETAYLAR
    public static String getAnnualDetail(LocalDate dateOfEmployment) {
        if (dateOfEmployment == null) {
            return "İşe başlama tarihi belirtilmemiş.";
        }

        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfEmployment, now);
        int years = period.getYears();

        int izinGunu;
        if (years < 1) {
            izinGunu = LESS_THAN_ONE_YEAR.getDays();
        } else if (years <= 10) {
            izinGunu = BETWEEN_ONE_AND_TEN_YEARS.getDays();
        } else {
            izinGunu = MORE_THAN_TEN_YEARS.getDays();
        }

        return String.format("""
            Yıllık izin haklarınız çalışma sürenize göre aşağıdaki gibidir:

             - 0 ile 1 yıl arası çalışma süresi: %d gün izin hakkı.
             - 1 ile 10 yıl arası çalışma süresi: %d gün izin hakkı.
             - 10 yıldan fazla çalışma süresi: %d gün izin hakkı.

            Mevcut çalışma süreniz: %d yıl, %d ay, %d gün.
            Bu nedenle yıllık izin hakkınız toplam %d gündür.
            """,
                LESS_THAN_ONE_YEAR.getDays(),
                BETWEEN_ONE_AND_TEN_YEARS.getDays(),
                MORE_THAN_TEN_YEARS.getDays(),
                period.getYears(),
                period.getMonths(),
                period.getDays(),
                izinGunu
        );
    }

    //KAÇ GÜN İZİNİ VAR ONU BULMA  YILLIK İZİN AYRINTI METODUNDAN ÇAĞIR.
    public static int getAnnualLeaveDays(LocalDate dateOfEmployment) {
        if (dateOfEmployment == null) {
            return 0; // işe başlama tarihi yoksa 0 izin günü veriyoruz
        }

        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfEmployment, now);
        int years = period.getYears();

        if (years < 1) {
            return LESS_THAN_ONE_YEAR.getDays();
        } else if (years <= 10) {
            return BETWEEN_ONE_AND_TEN_YEARS.getDays();
        } else {
            return MORE_THAN_TEN_YEARS.getDays();
        }
    }
}
