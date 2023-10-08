package uz.iq.iqgame.mapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.entity.enums.SystemRoleName;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.RegisterDTO;
import uz.iq.iqgame.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;

    public User toEntity(@NotNull RegisterDTO registerDTO) {
        return User.builder()
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .name(registerDTO.getName())
                .surname(registerDTO.getSurname())
                .status(statusRepository.findById(registerDTO.getStatus()).orElseThrow(() -> new NotFoundException("status topilmadi")))
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .enabled(false)
                .ball(0L)
                .systemRoleName(SystemRoleName.ROLE_USER).build();
    }


}
