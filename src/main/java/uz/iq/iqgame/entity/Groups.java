package uz.iq.iqgame.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;
import uz.iq.iqgame.repository.StatusRepository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
public class Groups extends AbsLongEntity {
    @Column(nullable = false)
    private String name;
}
