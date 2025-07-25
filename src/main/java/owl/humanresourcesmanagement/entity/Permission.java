package owl.humanresourcesmanagement.entity;


import owl.humanresourcesmanagement.enums.permissions.EPermissionState;
import owl.humanresourcesmanagement.enums.permissions.EPermissionType;
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
@Table(name = "tbl_permission")
public class Permission extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private LocalDate beginDate;
	private LocalDate endDate;
	@Enumerated(EnumType.STRING)
	private EPermissionState permissionState;
	@Enumerated(EnumType.STRING)
	private EPermissionType permissionType;
	private String description;
	
}
