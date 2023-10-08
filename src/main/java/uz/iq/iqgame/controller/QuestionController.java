package uz.iq.iqgame.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uz.iq.iqgame.payload.QuestionDTO;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.service.QuestionService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;


    @GetMapping("/getQuest/getQuestionByStatus/{statusId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionByStatus(@PathVariable Long statusId) {
        return questionService.getQuestionByStatus(statusId);
    }

    @GetMapping("/getQuest/getQuestion/{id}")
    public ResponseEntity<QuestionDTO> getById(@PathVariable Long id) {
        return questionService.getById(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @PostMapping("/addQuestion")
    public ResponseEntity<ApiResponse> addQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        return questionService.addQuestion(questionDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<ApiResponse>

    updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionDTO questionDTO) {
        return questionService.updateQuestion(id, questionDTO);
    }


    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }

}
