package uz.iq.iqgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.payload.AnswerDTO;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.service.AnswerService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/answer")
public class AnswerController {
    private final AnswerService answerService;


    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @PostMapping("/addAnswer")
    public ResponseEntity<ApiResponse> addAnswer(@RequestBody AnswerDTO answerDTO) {
        return answerService.addAnswer(answerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @PutMapping("/updateAnswer/{answerId}")
    public ResponseEntity<ApiResponse> updateAnswer(@RequestBody AnswerDTO answerDTO, @PathVariable Long answerId) {
        return answerService.updateAnswer(answerDTO, answerId);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @DeleteMapping("/deleteAnswer/{answerId}")
    public ResponseEntity<ApiResponse> deleteAnswer(@PathVariable Long answerId) {
        return answerService.deleteAnswer(answerId);
    }

}
