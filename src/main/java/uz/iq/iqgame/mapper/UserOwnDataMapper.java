package uz.iq.iqgame.mapper;

import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.GroupBySummarizationRenderingStrategy;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Status;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.payload.StatusDTO;
import uz.iq.iqgame.payload.UserOwnDataDTO;
import uz.iq.iqgame.repository.GroupRepository;
import uz.iq.iqgame.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class UserOwnDataMapper {
    private final StatusMapper statusMapper;

    public UserOwnDataDTO toDTO(User currentUser) {
        return UserOwnDataDTO.builder()
                .name(currentUser.getName())
                .surname(currentUser.getSurname())
                .email(currentUser.getEmail())
                .status(statusMapper.toDTO(currentUser.getStatus() != null  ? currentUser.getStatus() : Status.builder().build()))
                .userBall(currentUser.getBall())
                .isFinished(currentUser.getIsFinished())
                .groups(currentUser.getGroups())
                .build();
    }


}
