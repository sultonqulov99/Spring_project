package uz.iq.iqgame.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.iq.iqgame.entity.InterviewQuestionAnswer;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestionAnswer, Long> {
    Page<InterviewQuestionAnswer> findAllByLanguageId(Long language_id, Pageable pageable);
}
