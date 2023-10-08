package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Question;
import uz.iq.iqgame.entity.enums.SystemRoleName;
import uz.iq.iqgame.exception.AuthException;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.AnswerDTO;
import uz.iq.iqgame.repository.QuestionRepository;
import uz.iq.iqgame.repository.StatusRepository;
import uz.iq.iqgame.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final StatusRepository statusRepository;

    public void checkerRoleUser(Long groupId) {
        List<String> strings = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String role = strings.get(0);
        if (!(role.equals(String.valueOf(SystemRoleName.ROLE_ADMIN)))) {
            userRepository.findBySystemRoleNameAndGroupsId(SystemRoleName.valueOf(role), groupId).orElseThrow(() -> new AuthException("Xatolik mumkin bolmagan yolga murojat boldi"));
        }
    }

    public void checkRoleUser(AnswerDTO answerDTO) {
        checkerRoleUser(statusRepository.findById(questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("QuestionTopilmadi")).getStatus().getId()).orElseThrow(() -> new NotFoundException("Status topilmadi")).getGroups().getId());
    }
}
