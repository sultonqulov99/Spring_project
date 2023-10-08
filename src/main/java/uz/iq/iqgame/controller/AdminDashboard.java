package uz.iq.iqgame.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.entity.enums.SystemRoleName;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.mapper.AddRoleUserMapper;
import uz.iq.iqgame.payload.AddRoleUserDTO;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.LoginDTO;
import uz.iq.iqgame.repository.QuestionRepository;
import uz.iq.iqgame.repository.UserRepository;
import uz.iq.iqgame.service.AuthenticationService;
import uz.iq.iqgame.service.impl.CommonServiceImpl;

import java.util.*;


@RestController
@RequestMapping("/api/v1/auth/admindashboard")
@RequiredArgsConstructor
public class AdminDashboard {
    private final AuthenticationService authenticationService;
    private final QuestionRepository questionRepository;
    private final AddRoleUserMapper addRoleUserMapper;
    private final UserRepository userRepository;
    private final CommonServiceImpl commonService;

    @GetMapping
    public ResponseEntity<ApiResponse> adminAuth(@Valid @RequestBody LoginDTO loginDTO) {
        return authenticationService.adminAuth(loginDTO);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @DeleteMapping("/deleteAllDataByStatusId/{statusId}")
    public ResponseEntity<ApiResponse> deleteAllData(@PathVariable Long statusId) {
        User userbek = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commonService.checkerRoleUser(userbek.getGroups().getId());
        try {
            questionRepository.deleteAllByStatusId(statusId);
            for (User user : userRepository.findAllByStatusId(statusId)) {
                user.setIsFinished(false);
                user.setBall(0L);
                userRepository.save(user);
            }

            return ResponseEntity.ok(ApiResponse.builder().status(200).isSuccess(true).message("Barcha malumotlar o'chirildi").build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MainException("O'chirishda xatolik");
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllRoleName")
    public List<String> getALLRoleName() {
        List<String> strings = new LinkedList<>();
        for (SystemRoleName value : SystemRoleName.values()) {
            strings.add(String.valueOf(value));
        }

        return strings;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllRoleUser")
    public List<User> getAllRoleUser() {
        return userRepository.getAllRoleUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addRoleUser")
    public ResponseEntity<ApiResponse> addRoleUser(@Valid @RequestBody AddRoleUserDTO addRoleUserDTO) {
        if (userRepository.findByEmail(addRoleUserDTO.getEmail()).isPresent())
            throw new MainException("Bunday email foydalanilgan");
        userRepository.save(addRoleUserMapper.toEntity(addRoleUserDTO));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("User saqlandi admindashboard orqali tizimga kirsa boladi").isSuccess(true).build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteRoleUser/{userId}")
    public ResponseEntity<ApiResponse> deleteRoleUser(@PathVariable String userId) {
        try {
            UUID uuid = UUID.fromString(userId);
            userRepository.deleteById(uuid);
            return ResponseEntity.ok(ApiResponse.builder().status(200).message("O'chirildi").isSuccess(true).build());
        } catch (Exception e) {
            throw new MainException("Hatolik");
        }
    }
}

