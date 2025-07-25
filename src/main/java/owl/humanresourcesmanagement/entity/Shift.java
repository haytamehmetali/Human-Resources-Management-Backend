package owl.humanresourcesmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_shift")
public class Shift extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;

    private Long companyId;

    private Integer capacity;

    private String shiftName;

    private LocalTime beginHour;

    private LocalTime endHour;

    private String description;

    @ElementCollection
    @CollectionTable(name = "shift_users", joinColumns = @JoinColumn(name = "shift_id"))
    @Column(name = "user_id")
    private Set<Long> assignedUserIds = new HashSet<>();

    private String fullName;

}
