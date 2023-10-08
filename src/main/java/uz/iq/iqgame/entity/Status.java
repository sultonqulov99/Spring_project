package uz.iq.iqgame.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status extends AbsLongEntity {
    @Column(nullable = false)
    @NotNull(message = "Nomi bo'lishi kerak")
    private String name;
    @Column(nullable = false)
    private String timeExpired;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "status")
    private List<Question> questions;
    @ManyToOne
    private Groups groups;
}