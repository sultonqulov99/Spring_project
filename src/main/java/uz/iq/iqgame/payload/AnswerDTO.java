package uz.iq.iqgame.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AnswerDTO {
    private Boolean isCorrect;
    private String value;
    private Long questionId;
}
