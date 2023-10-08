package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Language;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.repository.LanguageRepository;
import uz.iq.iqgame.service.LanguageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    @Override
    public List<Language> getAllLanguage() {
        return languageRepository.findAll();
    }

    @Override
    public Language getLanguage(Long languageId) {
        return languageRepository.findById(languageId).orElseThrow(() -> new NotFoundException("Bunday til topilmadi"));
    }

    @Override
    public ResponseEntity<ApiResponse> addLanguage(Language language) {
        if (languageRepository.existsByProgrammingLanguageName(language.getProgrammingLanguageName()))
            throw new MainException("Bunday til avval qo'shilgan");

        languageRepository.save(language);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Til qo'shildi")
                .isSuccess(true)
                .status(201)
                .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponse> updateLanguage(Long languageId, Language language) {
        if (languageRepository.existsByProgrammingLanguageName(language.getProgrammingLanguageName()))
            throw new MainException("Bunday til avval qo'shilgan");

        Language editedLanguage = languageRepository.findById(languageId).orElseThrow(() -> new NotFoundException("Bunday til topilmadi"));
        editedLanguage.setProgrammingLanguageName(language.getProgrammingLanguageName());
        languageRepository.save(editedLanguage);
        return ResponseEntity.ok(ApiResponse.builder().message("Til yangilandi").isSuccess(true).status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteLanguage(Long languageId) {
        try {
            languageRepository.deleteById(languageId);
            return ResponseEntity.ok(ApiResponse.builder().message("Til o'chirildi").isSuccess(true).status(200).build());
        } catch (Exception e) {
            throw new MainException("Til o'chirishda xatolik");
        }
    }
}
