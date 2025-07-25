package owl.humanresourcesmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authId;

    @Column(unique = true, nullable = false, length = 512)
    private String token;

    private LocalDateTime expiryDate;
}
