package owl.humanresourcesmanagement.entity;

import owl.humanresourcesmanagement.enums.EState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@MappedSuperclass
public class BaseEntity {
    private Long create_at;
    private Long update_at;
    private EState state;

    @PrePersist
    protected void prePersist() {
        this.update_at = System.currentTimeMillis();
        this.create_at = System.currentTimeMillis();
        this.state = EState.ACTIVE;
    }
    
    @PreUpdate
    protected void preUpdate() {
        this.update_at = System.currentTimeMillis();
    }

}
