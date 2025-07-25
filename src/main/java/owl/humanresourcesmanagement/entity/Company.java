package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.company.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_company")
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(length = 300)
    private String address;
    
    @Column(unique = true, length = 25)
    private String phone;
    
    @Column(unique = true, length = 50)
    private String mail;
    
    private String logo;
    
    private Integer personalCount;
    
    private Boolean isMembershipActive;
    
    private LocalDate membershipStartDate;
    
    private LocalDate membershipEndDate;
    
    @Enumerated(EnumType.STRING)
    private ECompanyMembershipType companyMembershipType;
    
    @Enumerated(EnumType.STRING)
    private ECompanyType companyType;
    
    @Enumerated(EnumType.STRING)
    private ERegion region;
    
    @Enumerated(EnumType.STRING)
    private ECompanyState companyState;
    
}
