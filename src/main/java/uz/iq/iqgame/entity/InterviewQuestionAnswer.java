package uz.iq.iqgame.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class InterviewQuestionAnswer extends AbsLongEntity {
    @Column(nullable = false)
    private String questionCount;
    @Column(nullable = false)
    private String questionAnswer;
    @JoinColumn(nullable = false)
    @OneToOne
    private Language language;
}
