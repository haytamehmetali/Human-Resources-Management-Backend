package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.embezzlement.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_embezzlement")
public class Embezzlement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long companyId; //?
    private Long userId;
    private String brand;
    private String model;;
    private String serialNumber;
    private LocalDate assignedDate;
    private LocalDate returnedDate;
    private String name;

    @Column(length = 500)
    private String rejectReason;


    @Enumerated(EnumType.STRING)
    EEmbezzlementType embezzlementType;

    @Enumerated(EnumType.STRING)
    private EEmbezzlementState embezzlementState;

}
