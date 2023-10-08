package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.payload.QuestionDTO;
import uz.iq.iqgame.payload.ApiResponse;

import java.util.List;

public interface QuestionService {
    ResponseEntity<List<QuestionDTO>> getQuestionByStatus(Long statusId);

    ResponseEntity<QuestionDTO> getById(Long id);

    ResponseEntity<ApiResponse> addQuestion(QuestionDTO questionDTO);

    ResponseEntity<ApiResponse> updateQuestion(Long id, QuestionDTO questionDTO);

    ResponseEntity<ApiResponse> deleteQuestion(Long id);
}
