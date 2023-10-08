package uz.iq.iqgame.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.OwnDataDTO;
import uz.iq.iqgame.payload.UserDataDTO;
import uz.iq.iqgame.repository.AnswerRepository;
import uz.iq.iqgame.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnDataMapper {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public List<OwnDataDTO> toDto(List<UserDataDTO> userDataDTOS) {
        List<OwnDataDTO> list = new ArrayList<>();

        for (UserDataDTO userDataDTO : userDataDTOS) {
            OwnDataDTO build = OwnDataDTO
                    .builder()
                    .isSuccess(userDataDTO.getIsSuccess())
                    .questionValue(questionRepository.findById(userDataDTO.getQuestion()).orElseThrow(() -> new NotFoundException("Bunday savol topilmadi")).getTestValue())
                    .answerValue(answerRepository.findById(userDataDTO.getChosenAnswerDto()).orElseThrow(() -> new NotFoundException("Bunday javob topilmadi")).getValue())
                    .build();
            list.add(build);
        }

        return list;

    }
}
