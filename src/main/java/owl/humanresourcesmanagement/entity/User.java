package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.user.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private EmployeeInformation employeeInformation;
	
	@Column(unique = true, length = 25)
	private String phone;
	
	@Column(nullable = false, unique = true, length = 50)
	private String mail;
	
	@Column(nullable = false, unique = true, length = 50)
	private String pendingMail;
	
	@Column(nullable = false)
	private String password;
	
	private String avatar;

	@Enumerated(EnumType.STRING)
	private EUserState userState;
	
	@Enumerated(EnumType.STRING)
	private EUserRole role;
	
	private Boolean isFirstLogin;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ActivationCode activationCode;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ResetPasswordCode resetPasswordCode;

	@OneToOne(cascade = CascadeType.ALL)
	private ChangeMailCode changeMailCode;
}
