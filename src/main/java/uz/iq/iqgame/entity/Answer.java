package uz.iq.iqgame.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import uz.iq.iqgame.entity.Question;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
public class Answer extends AbsLongEntity {
    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Question question;
    private Boolean isCorrect;
    @Column(nullable = false)
    private String value;
}