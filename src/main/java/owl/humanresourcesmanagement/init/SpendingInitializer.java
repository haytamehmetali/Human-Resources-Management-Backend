package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.PersonalSpending;
import owl.humanresourcesmanagement.enums.spendings.ESpendingState;
import owl.humanresourcesmanagement.enums.spendings.ESpendingType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpendingInitializer {

    public static List<PersonalSpending> spendingInitializer() {
        List<PersonalSpending> spendings = new ArrayList<>();

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("Otelde konaklama")
                .billDocumentUrl(null)
                .billAmount(1500.00)
                .spendingDate(LocalDate.of(2025, 6, 15))
                .spendingState(ESpendingState.APPROVED)
                .spendingType(ESpendingType.ACCOMMODATION)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("İş yemeği")
                .billDocumentUrl(null)
                .billAmount(250.00)
                .spendingDate(LocalDate.of(2025, 6, 16))
                .spendingState(ESpendingState.PENDING)
                .spendingType(ESpendingType.MEAL)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(21L)
                .description("Ofis kırtasiye alımı")
                .billDocumentUrl(null)
                .billAmount(75.50)
                .spendingDate(LocalDate.of(2025, 5, 10))
                .spendingState(ESpendingState.REJECTED)
                .spendingType(ESpendingType.OFFICE_SUPPLIES)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(31L)
                .description("Şehir içi yolculuk")
                .billDocumentUrl(null)
                .billAmount(120.00)
                .spendingDate(LocalDate.of(2025, 7, 1))
                .spendingState(ESpendingState.APPROVED)
                .spendingType(ESpendingType.SOFTWARE)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(33L)
                .description("Akşam yemeği")
                .billDocumentUrl(null)
                .billAmount(300.00)
                .spendingDate(LocalDate.of(2025, 6, 30))
                .spendingState(ESpendingState.PENDING)
                .spendingType(ESpendingType.MEAL)
                .build());
        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("Araç yakıt gideri")
                .billDocumentUrl(null)
                .billAmount(400.00)
                .spendingDate(LocalDate.of(2025, 6, 1))
                .spendingState(ESpendingState.APPROVED)
                .spendingType(ESpendingType.MEAL)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("Ofis malzemeleri")
                .billDocumentUrl(null)
                .billAmount(150.00)
                .spendingDate(LocalDate.of(2025, 6, 1))  // Aynı gün
                .spendingState(ESpendingState.APPROVED)
                .spendingType(ESpendingType.OFFICE_SUPPLIES)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("Müşteri yemeği")
                .billDocumentUrl(null)
                .billAmount(350.00)
                .spendingDate(LocalDate.of(2025, 6, 5))
                .spendingState(ESpendingState.REJECTED)
                .spendingType(ESpendingType.MEAL)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("Toplantı için su ve atıştırmalık")
                .billDocumentUrl(null)
                .billAmount(75.00)
                .spendingDate(LocalDate.of(2025, 6, 5))  // Aynı gün
                .spendingState(ESpendingState.PENDING)
                .spendingType(ESpendingType.MEAL)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("Yazılım lisansı")
                .billDocumentUrl(null)
                .billAmount(600.00)
                .spendingDate(LocalDate.of(2025, 5, 28))
                .spendingState(ESpendingState.APPROVED)
                .spendingType(ESpendingType.SOFTWARE)
                .build());

        spendings.add(PersonalSpending.builder()
                .userId(11L)
                .description("İş seyahati otel masrafı")
                .billDocumentUrl(null)
                .billAmount(1200.00)
                .spendingDate(LocalDate.of(2025, 5, 28))  // Aynı gün
                .spendingState(ESpendingState.APPROVED)
                .spendingType(ESpendingType.ACCOMMODATION)
                .build());
        return spendings;
    }
}
