package uz.iq.iqgame.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.InterviewQuestionAnswer;
import uz.iq.iqgame.entity.Question;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.InterviewQuestionAnswerDTO;

import java.util.List;

public interface InterviewQuestionService {
    ResponseEntity<List<InterviewQuestionAnswer>> getInterviewQeustionPage(int page, int size, Long languageId);

    InterviewQuestionAnswer getById(Long interQuestid);

    ResponseEntity<ApiResponse> addInterviewQuestion(InterviewQuestionAnswerDTO interviewQuestionAnswer);

    ResponseEntity<ApiResponse> updateInterviewQuestion(Long interviewId, InterviewQuestionAnswerDTO interviewQuestionAnswerDTO);

    ResponseEntity<ApiResponse> deleteInterviewQuestion(Long interviewId);

}
