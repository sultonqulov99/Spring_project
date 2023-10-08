package uz.iq.iqgame.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "user_data")
public class UserData extends AbsLongEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
