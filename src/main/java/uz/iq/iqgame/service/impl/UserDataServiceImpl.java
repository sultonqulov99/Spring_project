package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Answer;
import uz.iq.iqgame.entity.Question;
import uz.iq.iqgame.entity.UserData;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.mapper.OwnDataMapper;
import uz.iq.iqgame.mapper.UserDataMapper;
import uz.iq.iqgame.mapper.UserRatingMapper;
import uz.iq.iqgame.payload.*;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.repository.AnswerRepository;
import uz.iq.iqgame.repository.QuestionRepository;
import uz.iq.iqgame.repository.UserDataRepository;
import uz.iq.iqgame.repository.UserRepository;
import uz.iq.iqgame.service.UserDataService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {
    private final UserRepository userRepository;
    private final UserRatingMapper userRatingMapper;
    private final UserDataRepository userDataRepository;
    private final UserDataMapper userDataMapper;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final OwnDataMapper ownDataMapper;

    @Override
    public ResponseEntity<List<UserRatingDTO>> userRating(Long statusId, Integer page, Integer size) {
        return ResponseEntity.ok(userRepository.findAllByStatusId(PageRequest.of(page, size, Sort.by("ball").descending()), statusId).stream().map(userRatingMapper::toRating).toList());
    }

    @Override
    public ResponseEntity<List<OwnDataDTO>> getOwnData() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserDataDTO> userData = userDataRepository.findAllByUserId(userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Foydalanuvchi topilmadi")).getId()).stream().map(userDataMapper::toDto).toList();
        return ResponseEntity.ok(ownDataMapper.toDto(userData));
    }

    @Override
    public ResponseEntity<ApiResponse> addUserData(UserDataForSave userDataForSave) {
        Question question = questionRepository.findById(userDataForSave.getQuestionId()).orElseThrow(() -> new NotFoundException("Savol topilmadi"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Foydalanuvchi topilmadi"));

        if (!Objects.equals(user.getStatus().getId(), question.getStatus().getId())) {
            throw new MainException("Siz ushbu savolga javob bera olmaysiz");
        }


        if (userDataRepository.countUserDataByUserId(user.getId()) > 20) {
            user.setIsFinished(true);
            userRepository.save(user);
            throw new MainException("Siz barcha savollarga javob berib bo'ldingiz");
        }

        if (userDataRepository.existsByQuestionIdAndUserId(question.getId(), user.getId()))
            throw new MainException("Bu savolga allaqachon javob bergansiz");

        Answer userAnswer = answerRepository.findByQuestionIdAndId(userDataForSave.getQuestionId(), userDataForSave.getChosenAnswerId()).orElseThrow(() -> new NotFoundException("Bu boshqa savolga tegishli javob"));
        List<Answer> answers = question.getAnswers();
        boolean answerIsCorrect = false;
        for (Answer answer : answers) {
            if (answer.equals(userAnswer) && answer.getIsCorrect()) {
                user.setBall(question.getSavolBall() + user.getBall());
                userRepository.save(user);
                answerIsCorrect = true;
                break;
            }
        }

        userDataRepository.save(UserData
                .builder()
                .question(question)
                .answer(userAnswer)
                .user(user)
                .build());

        return ResponseEntity.ok(ApiResponse
                .builder()
                .isSuccess(true)
                .status(200)
                .message("Ushbu savolga " + (answerIsCorrect ? "tog'ri" : "xato") + " javob berdingiz")
                .build());
    }


}
