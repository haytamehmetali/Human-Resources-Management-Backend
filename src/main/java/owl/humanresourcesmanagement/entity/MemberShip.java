package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.company.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false) //true ?
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_member_ship")
public class MemberShip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long companyId;
	
	private Boolean isMembershipActive; // Enum -> ACTIVE / PASSIVE / PAUSED / NONE (default)
	
	private ECompanyMembershipType companyMembershipType;
	
}
