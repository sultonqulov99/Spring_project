package uz.iq.iqgame.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.InterviewQuestionAnswer;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.InterviewQuestionAnswerDTO;
import uz.iq.iqgame.repository.LanguageRepository;

@Service
@RequiredArgsConstructor
public class InterviewQuestionMapper {
    private final LanguageRepository languageRepository;

    public InterviewQuestionAnswer toEntity(InterviewQuestionAnswerDTO interviewQuestionAnswerDTO) {

        return InterviewQuestionAnswer
                .builder()
                .questionAnswer(interviewQuestionAnswerDTO.getQuestionAnswer())
                .questionCount(interviewQuestionAnswerDTO.getQuestionCount())
                .language(languageRepository.findById(interviewQuestionAnswerDTO.getLanguageId()).orElseThrow(() -> new NotFoundException("O'zgartirayotgan til topilmadi")))
                .build();
    }
}
