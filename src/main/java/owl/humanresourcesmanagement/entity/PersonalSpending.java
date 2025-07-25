package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.spendings.ESpendingState;
import owl.humanresourcesmanagement.enums.spendings.ESpendingType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_personal_spending")
public class PersonalSpending extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String description;

    /**
     * Harcama belgesinin sunucudaki yolu veya public erişim URL’si
     */
    private String billDocumentUrl;

    private Double billAmount;

    private LocalDate spendingDate;

    @Enumerated(EnumType.STRING)
    private ESpendingState spendingState;

    @Enumerated(EnumType.STRING)
    private ESpendingType spendingType;
}
