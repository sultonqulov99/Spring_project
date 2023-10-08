package uz.iq.iqgame.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.entity.Language;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.service.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/language")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping("/getAllLanguage")
    public List<Language> getAllLanguage() {
        return languageService.getAllLanguage();
    }


    @GetMapping("/getLanguage/{languageId}")
    public Language getLanguage(@PathVariable Long languageId) {
        return languageService.getLanguage(languageId);
    }

    @PreAuthorize("hasAnyRole('ROLE_INTERVIEW' , 'ROLE_ADMIN')")
    @PostMapping("/addLanguage")
    public ResponseEntity<ApiResponse> addLanguage(@Valid @RequestBody Language language) {
        return languageService.addLanguage(language);
    }


    @PreAuthorize("hasAnyRole('ROLE_INTERVIEW' , 'ROLE_ADMIN')")
    @PutMapping("/updateLanguage/{languageId}")
    public ResponseEntity<ApiResponse> updateLanguage(@Valid @PathVariable Long languageId, @RequestBody Language language) {
        return languageService.updateLanguage(languageId, language);
    }

    @PreAuthorize("hasAnyRole('ROLE_INTERVIEW' , 'ROLE_ADMIN')")
    @DeleteMapping("/deleteLanguage/{languageId}")
    public ResponseEntity<ApiResponse> deleteLanguage(@PathVariable Long languageId) {
        return languageService.deleteLanguage(languageId);
    }

}
