package uz.iq.iqgame.repository;

import ch.qos.logback.core.status.StatusUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.iq.iqgame.entity.Status;

import java.util.List;


public interface StatusRepository extends JpaRepository<Status,Long> {
    boolean existsByName(String name);

    List<Status> findAllByGroupsId(Long groups_id);
    boolean existsByGroupsIdAndId(Long groups_id, Long id);
}
