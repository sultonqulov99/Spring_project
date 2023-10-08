package uz.iq.iqgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.entity.UserData;
import uz.iq.iqgame.payload.UserDataDTO;

import java.util.List;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData,Long> {
    List<UserData> findAllByUserId(UUID user_id);
    boolean existsByQuestionIdAndUserId(Long question_id, UUID user_id);
    Integer countUserDataByUserId(UUID user_id);
    void deleteUserDataByUser(User user);
}
