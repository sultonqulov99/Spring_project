package uz.iq.iqgame.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.entity.enums.SystemRoleName;
import uz.iq.iqgame.payload.UserRatingDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    Page<User> findAllByStatusId(Pageable pageable, Long id);

    Optional<User> findByEmail(String username);

    Boolean existsByEmailAndEnabledFalse(String email);

    Boolean existsByEmailAndEnabledTrue(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    Optional<User> findBySystemRoleNameAndGroupsId(SystemRoleName systemRoleName, Long groups_id);
    @Query(nativeQuery = true  , value = "select * from users where status_id =:statusid")
    List<User> findAllByStatusId(@Param("statusid") Long statusid);
    @Query( nativeQuery = true , value = "select * from users where system_role_name!='ROLE_USER'")
    List<User> getAllRoleUser();

}
