package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.company.*;
import java.util.*;

public class CompanyInitializer {
    public static List<Company> companyInitializer() {
        Company company1 = Company.builder()
                .name("TechNova")
                .mail("info@technova.com")
                .phone("03120001122")
                .personalCount(50)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company2 = Company.builder()
                .name("GreenSolutions")
                .phone("04160003344")
                .mail("contact@greensolutions.com")
                .personalCount(30)
                .companyState(ECompanyState.ACCEPTED)
                .build();
        Company company3 = Company.builder()
                .name("SkyNet Systems")
                .phone("02124567890")
                .mail("support@skynet.com")
                .personalCount(120)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company4 = Company.builder()
                .name("FutureSoft")
                .phone("03225554477")
                .mail("hello@futuresoft.io")
                .personalCount(70)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company5 = Company.builder()
                .name("UrbanTech")
                .phone("05553334455")
                .mail("info@urbantech.co")
                .personalCount(45)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company6 = Company.builder()
                .name("EcoWare")
                .phone("05001231234")
                .mail("contact@ecoware.org")
                .personalCount(25)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company7 = Company.builder()
                .name("NeonEdge")
                .phone("05449887766")
                .mail("support@neonedge.ai")
                .personalCount(80)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company8 = Company.builder()
                .name("SolarCore")
                .phone("05330001122")
                .mail("info@solarcore.com")
                .personalCount(60)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company9 = Company.builder()
                .name("CodeNation")
                .phone("05001112233")
                .mail("team@codenation.dev")
                .personalCount(95)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        Company company10 = Company.builder()
                .name("PixelPeak")
                .phone("03124443355")
                .mail("contact@pixelpeak.com")
                .personalCount(40)
                .companyState(ECompanyState.ACCEPTED)
                .build();

        return List.of(
                company1, company2, company3, company4, company5,
                company6, company7, company8, company9, company10
        );
    }
}
