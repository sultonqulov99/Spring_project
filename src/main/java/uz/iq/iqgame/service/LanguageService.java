package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.Language;
import uz.iq.iqgame.payload.ApiResponse;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguage();
    Language getLanguage(Long languageId);

    ResponseEntity<ApiResponse> addLanguage(Language language);
    ResponseEntity<ApiResponse> updateLanguage(Long languageId, Language language);

    ResponseEntity<ApiResponse> deleteLanguage(Long languageId);
}
