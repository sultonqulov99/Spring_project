package uz.iq.iqgame.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.payload.UserRatingDTO;

@Service
@RequiredArgsConstructor
public class UserRatingMapper {
    private final StatusMapper statusMapper;
    public UserRatingDTO toRating(User user){
        return UserRatingDTO.builder().ball(user.getBall()).surname(user.getSurname()).name(user.getName()).status(statusMapper.toDTO(user.getStatus())).build();
    }
}
