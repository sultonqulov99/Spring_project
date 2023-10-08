package uz.iq.iqgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.iq.iqgame.entity.Groups;

public interface GroupRepository extends JpaRepository<Groups, Long> {
    Boolean existsByName(String name);
}
