package uz.iq.iqgame.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    @Null(message = "id kerak emas")
    private Long id;
    @NotNull(message = "Ball kiriting")
    private Integer savolBall;
    @NotNull(message = "Test qiymatini kiriting")
    private String testValue;
    @NotNull(message = "Sarlavhani kiriting")
    private String title;
    @NotNull(message = "Status id ni kiriting")
    private Long statusId;
    @Null(message = "answer kerak emas")
    private List<AnswerDTO> answers;
}
