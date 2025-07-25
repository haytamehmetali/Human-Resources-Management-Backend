package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.company.ECommentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID üzerinden ilişki
    @Column(name = "company_id", nullable = false, unique = true)
    private Long companyId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(length = 1000)
    private String content;

    private String position;

    @Enumerated(EnumType.STRING)
    private ECommentStatus status; // PENDING - APPROVED - REJECTED



}
