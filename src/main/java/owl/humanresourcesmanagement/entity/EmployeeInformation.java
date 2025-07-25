package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.user.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.time.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_employee_information")
public class EmployeeInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long companyId;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;
    
    @Column(unique = true, length = 50)
    private String mail;
    
    private String position;
    
    @Enumerated(EnumType.STRING)
    private EWorkType workType;
    
    @Enumerated(EnumType.STRING)
    private EMaritalStatus maritalStatus;
    
    @Enumerated(EnumType.STRING)
    private EEducationLevel educationLevel;
    
    @Column(unique = true, length = 25)
    private String identityNumber;
    
    private LocalDate dateOfBirth;
    
    @Column(length = 300)
    private String address;
    
    @Enumerated(EnumType.STRING)
    private EGender gender;
    
    private LocalDate dateOfEmployment;
    
    private LocalDate dateOfTermination;
    
    private Double salary;

    @Enumerated(EnumType.STRING)
    private EEmploymentStatus employmentStatus;
    
    private Boolean isCriminalRecord;

}
