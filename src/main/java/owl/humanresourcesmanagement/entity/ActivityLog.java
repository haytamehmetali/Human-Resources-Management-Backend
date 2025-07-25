package owl.humanresourcesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_activity_log")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long userId; // İşlemi yapan kullanıcı

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long affectedUserId; // Etkilenen kullanıcı (varsa)
    private Long affectedCompanyId; // Etkilenen şirket (varsa)

    // JSON formatında ek detaylar
    @Column(columnDefinition = "TEXT")
    private String additionalData;
}
