package uz.iq.iqgame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDeleteAction;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;
import org.hibernate.annotations.OnDelete;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"status_id", "title"})})
public class Question extends AbsLongEntity {
    @Column(nullable = false)
    private Integer savolBall;
    @Column(nullable = false, columnDefinition = "text")
    private String testValue;
    @Column(nullable = false)
    private String title;
    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Status status;
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Answer> answers;
}
