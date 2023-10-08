package uz.iq.iqgame.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Answer;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.AnswerDTO;
import uz.iq.iqgame.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class AnswerMapper {
    private final QuestionRepository questionRepository;

    public AnswerDTO toDto(Answer answer) {
        return AnswerDTO
                .builder()
                .isCorrect(null)
                .value(answer.getValue())
                .questionId(answer.getQuestion().getId())
                .build();
    }

    public Answer toEntity(AnswerDTO answerDTO) {
        return Answer
                .builder()
                .isCorrect(answerDTO.getIsCorrect())
                .value(answerDTO.getValue())
                .question(questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("Savol topilmadi")))
                .build();
    }
}
