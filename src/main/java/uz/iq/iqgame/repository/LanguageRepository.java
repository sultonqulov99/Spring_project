package uz.iq.iqgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.iq.iqgame.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Boolean existsByProgrammingLanguageName(String programmingLanguageName);
}
