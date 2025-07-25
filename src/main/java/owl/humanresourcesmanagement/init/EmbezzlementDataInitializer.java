package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.Embezzlement;
import owl.humanresourcesmanagement.enums.embezzlement.EEmbezzlementState;
import owl.humanresourcesmanagement.enums.embezzlement.EEmbezzlementType;

import java.time.LocalDate;
import java.util.List;


public class EmbezzlementDataInitializer {

    public static List<Embezzlement> embezzlementInitializer() {
        List<Embezzlement> embezzlements = List.of(

                Embezzlement.builder()
                        .name("Laptop")
                        .description("Geliştiriciye zimmetlendi.")
                        .companyId(1L)
                        .userId(1L)
                        .embezzlementType(EEmbezzlementType.ELECTRONIC_DEVICES)
                        .embezzlementState(EEmbezzlementState.APPROVED)
                        .brand("Apple")
                        .model("Macbook Pro")
                        .serialNumber("7783837")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build(),
                    Embezzlement.builder()
                            .name("Laptop")
                            .description("Geliştiriciye zimmetlendi.")
                            .companyId(1L)
                            .userId(11L)
                            .embezzlementType(EEmbezzlementType.ELECTRONIC_DEVICES)
                            .embezzlementState(EEmbezzlementState.APPROVED)
                            .brand("Apple")
                            .model("Macbook Pro")
                            .serialNumber("7783837")
                            .assignedDate(LocalDate.of(2024, 12, 1))
                            .build(),
                Embezzlement.builder()
                        .name("Telefon")
                        .description("Bakım ekibine zimmetlendi.")
                        .companyId(1L)
                        .userId(21L)
                        .embezzlementType(EEmbezzlementType.ELECTRONIC_DEVICES)
                        .embezzlementState(EEmbezzlementState.APPROVED)
                        .brand("Apple")
                        .model("13 Pro")
                        .serialNumber("76563837")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build(),

                Embezzlement.builder()
                        .name("Ofis Masası")
                        .description("Satış personeline teslim edildi.")
                        .companyId(2L)
                        .userId(32L)
                        .embezzlementType(EEmbezzlementType.OFFICE_EQUIPMENT)
                        .embezzlementState(EEmbezzlementState.OTHER)
                        .brand("Bellona")
                        .model("Çam")
                        .serialNumber("7783987")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build(),

                Embezzlement.builder()
                        .name("Ofis Sandalyesi")
                        .description("Yeni çalışan için ayrıldı.")
                        .companyId(3L)
                        .userId(33L)
                        .embezzlementType(EEmbezzlementType.OFFICE_EQUIPMENT)
                        .embezzlementState(EEmbezzlementState.PENDING)
                        .brand("Kare Ofis")
                        .model("Yıldız Servisi")
                        .serialNumber("7783833847")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build(),

                Embezzlement.builder()
                        .name("Laptop")
                        .description("Geliştiriciye zimmetlendi.")
                        .companyId(4L)
                        .userId(34L)
                        .embezzlementType(EEmbezzlementType.ELECTRONIC_DEVICES)
                        .embezzlementState(EEmbezzlementState.PENDING)
                        .brand("Apple")
                        .model("Macbook Air")
                        .serialNumber("64483837")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build(),

                Embezzlement.builder()
                        .name("Telefon")
                        .description("Bakım ekibine zimmetlendi.")
                        .companyId(5L)
                        .userId(35L)
                        .embezzlementType(EEmbezzlementType.ELECTRONIC_DEVICES)
                        .embezzlementState(EEmbezzlementState.APPROVED)
                        .brand("Apple")
                        .model("16 Pro Max")
                        .serialNumber("75323837")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build(),

                Embezzlement.builder()
                        .name("Ofis Masası")
                        .description("Satış personeline teslim edildi.")
                        .companyId(2L)
                        .userId(32L)
                        .embezzlementType(EEmbezzlementType.OFFICE_EQUIPMENT)
                        .embezzlementState(EEmbezzlementState.OTHER)
                        .brand("Zivella")
                        .model("Mio")
                        .serialNumber("12283837")
                        .assignedDate(LocalDate.of(2024, 9, 4))
                        .build(),

                Embezzlement.builder()
                        .name("Ofis Sandalyesi")
                        .description("Yeni çalışan için ayrıldı.")
                        .companyId(2L)
                        .userId(42L)
                        .embezzlementType(EEmbezzlementType.OFFICE_EQUIPMENT)
                        .embezzlementState(EEmbezzlementState.OTHER)
                        .brand("Zivella")
                        .model("Tim")
                        .serialNumber("13483837")
                        .assignedDate(LocalDate.of(2024, 12, 1))
                        .build()


        );

        return embezzlements;
    }
}
