package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.Answer;
import uz.iq.iqgame.payload.AnswerDTO;
import uz.iq.iqgame.payload.ApiResponse;

import java.util.List;

public interface AnswerService {
    ResponseEntity<ApiResponse> addAnswer(AnswerDTO answerDTO);

    ResponseEntity<ApiResponse> updateAnswer(AnswerDTO answerDTO, Long answerId);

    ResponseEntity<ApiResponse> deleteAnswer(Long answerId);


}
