package uz.iq.iqgame.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.iq.iqgame.entity.Answer;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnDataDTO {
    private String questionValue;
    private String answerValue;
    private Boolean isSuccess;
}
