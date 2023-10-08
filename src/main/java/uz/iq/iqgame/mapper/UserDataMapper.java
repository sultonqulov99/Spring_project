package uz.iq.iqgame.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Answer;
import uz.iq.iqgame.entity.UserData;
import uz.iq.iqgame.payload.UserDataDTO;
import uz.iq.iqgame.repository.AnswerRepository;
import uz.iq.iqgame.repository.QuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDataMapper {
    private final QuestionRepository questionRepository;

    public UserDataDTO toDto(UserData userData){
        List<Answer> answers = userData.getQuestion().getAnswers();

        boolean isCorrect = false;
        for (Answer answer : answers) {
            if (answer.getIsCorrect() && userData.getAnswer().equals(answer)) {
                isCorrect = true;
                break;
            }
        }

        return UserDataDTO
                .builder()
                .question(userData.getQuestion().getId())
                .chosenAnswerDto(userData.getAnswer().getId())
                .isSuccess(isCorrect)
                .build();
    }
}
