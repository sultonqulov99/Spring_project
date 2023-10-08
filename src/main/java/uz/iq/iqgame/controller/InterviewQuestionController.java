package uz.iq.iqgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.entity.InterviewQuestionAnswer;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.InterviewQuestionAnswerDTO;
import uz.iq.iqgame.service.InterviewQuestionService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/interviewQuestion")
@RequiredArgsConstructor
public class InterviewQuestionController {
    private final InterviewQuestionService interviewQuestionService;


    @GetMapping("/getInterviewQuestionPageByLanguageId/{languageId}")
    public ResponseEntity<List<InterviewQuestionAnswer>> getInterviewQuestionPage(@RequestParam int page, @RequestParam int size, @PathVariable Long languageId) {
        return interviewQuestionService.getInterviewQeustionPage(page, size, languageId);
    }


    @GetMapping("/getInterviewQuestionById/{interQuestid}")
    public InterviewQuestionAnswer getById(@PathVariable Long interQuestid) {
        return interviewQuestionService.getById(interQuestid);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ROLE_INTERVIEW')")
    @PostMapping("/addInterviewQuestion")
    public ResponseEntity<ApiResponse> addInterviewQuestion(@RequestBody InterviewQuestionAnswerDTO interviewQuestionAnswerDTO) {
        return interviewQuestionService.addInterviewQuestion(interviewQuestionAnswerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ROLE_INTERVIEW')")
    @PutMapping("/updateInterviewQuestion/{interviewId}")
    public ResponseEntity<ApiResponse> updateInterviewQuestion(@PathVariable Long interviewId, @RequestBody InterviewQuestionAnswerDTO interviewQuestionAnswer) {
        return interviewQuestionService.updateInterviewQuestion(interviewId, interviewQuestionAnswer);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ROLE_INTERVIEW')")
    @DeleteMapping("/deleteInterviewQuestion/{interviewId}")
    public ResponseEntity<ApiResponse> deleteInterviewQuestion(@PathVariable Long interviewId) {
        return interviewQuestionService.deleteInterviewQuestion(interviewId);
    }
}
