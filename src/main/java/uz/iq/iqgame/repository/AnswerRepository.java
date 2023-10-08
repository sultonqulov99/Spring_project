package uz.iq.iqgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.iq.iqgame.entity.Answer;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "SELECT count(a) FROM Answer a WHERE a.question.id =:questionId")
    Integer countByQuestionId(@Param("questionId") Long questionId);

    Optional<Answer> findByQuestionIdAndId(Long question_id, Long id);

    Boolean existsByValueAndQuestionId(String value, Long question_id);
}
