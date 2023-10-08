package uz.iq.iqgame.mapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Question;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.QuestionDTO;
import uz.iq.iqgame.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class QuestionMapper {
    private final StatusRepository statusRepository;
    private final AnswerMapper answerMapper;

    public Question toEntity(QuestionDTO questionDTO){
        return Question
                .builder()
                .title(questionDTO.getTitle())
                .status(statusRepository.findById(questionDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Status topilmadi")))
                .savolBall(questionDTO.getSavolBall())
                .testValue(questionDTO.getTestValue())
                .answers(questionDTO.getAnswers() == null ? null : questionDTO.getAnswers().stream().map(answerMapper::toEntity).toList())
                .build();
    }

    public QuestionDTO toDto(@NotNull Question question) {
        return QuestionDTO
                .builder()
                .id(question.getId())
                .title(question.getTitle())
                .statusId(question.getStatus() == null ? null : question.getStatus().getId())
                .savolBall(question.getSavolBall())
                .testValue(question.getTestValue())
                .build();
    }
    public QuestionDTO toDTO(@NotNull Question question) {
        return QuestionDTO
                .builder()
                .id(question.getId())
                .title(question.getTitle())
                .statusId(question.getStatus() == null ? null : question.getStatus().getId())
                .savolBall(question.getSavolBall())
                .testValue(question.getTestValue())
                .answers(question.getAnswers().stream().map(answerMapper::toDto).toList())
                .build();
    }
}
