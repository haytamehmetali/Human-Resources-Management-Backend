package owl.humanresourcesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import owl.humanresourcesmanagement.enums.EBreakType;

import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_break")
public class Break extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long breakId;
	
	private Long companyId;
	
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	private EBreakType breakType;
	
	private LocalTime beginTime;
	
	private LocalTime endTime;
	
	private String description;
}
