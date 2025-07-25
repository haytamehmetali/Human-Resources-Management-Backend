package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.publicHoliday.EPublicHolidays;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_public_holidays")
public class PublicHolidays extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	@Column(nullable = false)
	private String holidaysName;
	
	@Column(nullable = false)
	private LocalDate startDate;
	
	@Column(nullable = false)
	private LocalDate endDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EPublicHolidays publicHolidays;
	
	private String description;
}
