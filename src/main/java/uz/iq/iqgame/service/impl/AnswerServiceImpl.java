package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Answer;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.mapper.AnswerMapper;
import uz.iq.iqgame.payload.AnswerDTO;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.repository.AnswerRepository;
import uz.iq.iqgame.service.AnswerService;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final CommonServiceImpl commonService;


    @Override
    public ResponseEntity<ApiResponse> addAnswer(AnswerDTO answerDTO) {
        commonService.checkRoleUser(answerDTO);
        if (answerRepository.countByQuestionId(answerDTO.getQuestionId()) >= 4)
            throw new MainException("Ushbu savolga qoshiladigan javoblar qiymati to'ldi");
        checkRowAnswer(answerDTO);
        answerRepository.save(answerMapper.toEntity(answerDTO));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Javob ushbu savolga qo'shildi").isSuccess(true).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateAnswer(AnswerDTO answerDTO, Long answerId) {
        commonService.checkRoleUser(answerDTO);
        if (!answerRepository.existsById(answerId))
            throw new NotFoundException("O'zgartirmoqchi bolgan javobingiz topilmadi");

        Answer entity = answerMapper.toEntity(answerDTO);
        entity.setId(answerId);
        try {
            answerRepository.save(entity);
        } catch (Exception e) {
            throw new MainException("Bunday value qo'shilgan");
        }
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Javobingiz o'zgartirildi").isSuccess(true).build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteAnswer(Long answerId) {
        commonService.checkRoleUser(answerMapper.toDto(answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("Answer topilmadi"))));
        try {
            Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("Answer Topilmadi"));

            answerRepository.deleteById(answerId);
            return ResponseEntity.ok(ApiResponse.builder().status(200).message("O'chirildi").isSuccess(true).build());
        } catch (Exception e) {
            throw new MainException("O'chirishda qandaydir xatolik");
        }
    }


    public void checkRowAnswer(AnswerDTO answerDTO) {
        if (answerRepository.existsByValueAndQuestionId(answerDTO.getValue(), answerDTO.getQuestionId()))
            throw new MainException("Ushbu savolda shunday qiymatdagi javob mavjud");
    }
}
