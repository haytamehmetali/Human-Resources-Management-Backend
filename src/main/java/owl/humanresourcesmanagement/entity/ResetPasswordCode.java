package owl.humanresourcesmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_reset_password_code")
public class ResetPasswordCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	private LocalDateTime expirationTime;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
