package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.InterviewQuestionAnswer;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.mapper.InterviewQuestionMapper;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.InterviewQuestionAnswerDTO;
import uz.iq.iqgame.repository.InterviewQuestionRepository;
import uz.iq.iqgame.service.InterviewQuestionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewQuestionImpl implements InterviewQuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewQuestionMapper interviewQuestionMapper;

    @Override
    public ResponseEntity<List<InterviewQuestionAnswer>> getInterviewQeustionPage(int page, int size, Long languageId) {
        return ResponseEntity.ok(interviewQuestionRepository.findAllByLanguageId(languageId, PageRequest.of(page, size, Sort.by("id"))).stream().toList());
    }

    @Override
    public InterviewQuestionAnswer getById(Long interQuestid) {
        return interviewQuestionRepository.findById(interQuestid).orElseThrow(() -> new NotFoundException("Bunday savol topilmadi"));
    }

    @Override
    public ResponseEntity<ApiResponse> addInterviewQuestion(InterviewQuestionAnswerDTO interviewQuestionAnswerDTO) {
        interviewQuestionRepository.save(interviewQuestionMapper.toEntity(interviewQuestionAnswerDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Interview Question qo'shildi").isSuccess(true).status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateInterviewQuestion(Long interviewId, InterviewQuestionAnswerDTO interviewQuestionAnswerDTO) {
        interviewQuestionRepository.findById(interviewId).orElseThrow(() -> new NotFoundException("Bunday interview Question topilmadi"));
        InterviewQuestionAnswer entity = interviewQuestionMapper.toEntity(interviewQuestionAnswerDTO);
        entity.setId(interviewId);
        interviewQuestionRepository.save(entity);
        return ResponseEntity.ok(ApiResponse.builder().message("Interview Question yangilandi").isSuccess(true).status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteInterviewQuestion(Long interviewId) {
        try {
            interviewQuestionRepository.deleteById(interviewId);
            return ResponseEntity.ok(ApiResponse.builder().message("Interview Question o'chirildi").status(200).isSuccess(true).build());
        } catch (Exception e) {
            throw new MainException("Interview Question o'chirildi");
        }
    }
}
