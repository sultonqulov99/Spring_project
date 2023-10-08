package uz.iq.iqgame.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewQuestionAnswerDTO {
    @NotNull(message = "barcha malumotlar kiritilsin")
    private String questionCount;
    @NotNull(message = "barcha malumotlar kiritilsin")
    private String questionAnswer;
    @NotNull(message = "barcha malumotlar kiritilsin")
    private Long languageId;
}
