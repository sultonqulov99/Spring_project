package uz.iq.iqgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.iq.iqgame.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByStatusId(Long id);
    boolean existsByTitleAndStatusId(String title, Long statusId);
    @Query(value = "SELECT count(q) FROM Question q WHERE q.status.id =:statusId")
    Integer countByStatusId(@Param("statusId") Long statusId);


    void deleteAllByStatusId(Long status_id);



}
