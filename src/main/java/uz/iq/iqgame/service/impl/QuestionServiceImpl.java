package uz.iq.iqgame.service.impl;

import uz.iq.iqgame.entity.Question;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.QuestionDTO;
import uz.iq.iqgame.mapper.QuestionMapper;
import uz.iq.iqgame.payload.ApiResponse;

import uz.iq.iqgame.repository.QuestionRepository;
import uz.iq.iqgame.repository.StatusRepository;
import uz.iq.iqgame.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final StatusRepository statusRepository;
    private final QuestionMapper questionMapper;
    private final CommonServiceImpl commonService;

    @Override
    public ResponseEntity<List<QuestionDTO>> getQuestionByStatus(Long statusId) {
        return ResponseEntity.ok(questionRepository.findByStatusId(statusId).stream().map(questionMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<QuestionDTO> getById(Long id) {
        return ResponseEntity.ok(questionMapper.toDTO(questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Savol topilmadi"))));
    }

    @Override
    public ResponseEntity<ApiResponse> addQuestion(QuestionDTO questionDTO) {
        commonService.checkerRoleUser(statusRepository.findById(questionDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status topilmadi")).getGroups().getId());

        if (questionRepository.countByStatusId(questionDTO.getStatusId()) >= 20)
            throw new MainException("Ushbu statusga boshqa savol qo'shish mumkin emas");
        checkQuestion(questionDTO);
        questionRepository.save(Question.builder()
                .title(questionDTO.getTitle())
                .savolBall(questionDTO.getSavolBall())
                .testValue(questionDTO.getTestValue())
                .status(statusRepository.findById(questionDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Status yoq")))
                .build());
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Savol qo'shildi").isSuccess(true).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateQuestion(Long id, QuestionDTO questionDTO) {
        commonService.checkerRoleUser(statusRepository.findById(questionDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status topilmadi")).getGroups().getId());
        Integer integer = questionRepository.countByStatusId(questionDTO.getStatusId());
        if (integer >= 20) {
            throw new MainException("O'zgartirmoqchi bolgan statusingizga savol berishi to'lgan");
        }
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Savol topilmadi"));

        Question entity = questionMapper.toEntity(questionDTO);
        entity.setId(question.getId());
        try {
            questionRepository.save(entity);
        } catch (Exception e) {
            throw new MainException("Bunday title qo'shilgan");
        }

        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(200)
                .isSuccess(true)
                .message("Savol yangilandi")
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteQuestion(Long id) {
        try {
            commonService.checkerRoleUser(statusRepository.findById(questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question topilmadi")).getStatus().getId()).orElseThrow(() -> new NotFoundException("Bunday status topilmadi")).getGroups().getId());
            questionRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.builder().status(200).isSuccess(true).message("Savol o'chirildi").build());
        } catch (Exception e) {
            throw new MainException("Savol o'chirishda xatolik");
        }
    }


    private void checkQuestion(QuestionDTO questionDTO) {
        if (questionRepository.existsByTitleAndStatusId(questionDTO.getTitle(), questionDTO.getStatusId()))
            throw new MainException("Bunday nomli title ushbu statusga qo'shilgan");
    }


}
