package uz.iq.iqgame.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.entity.enums.SystemRoleName;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.AddRoleUserDTO;
import uz.iq.iqgame.repository.GroupRepository;
import uz.iq.iqgame.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class AddRoleUserMapper {
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;

    public User toEntity(AddRoleUserDTO addRoleUserDTO) {
        return User.builder()
                .isFinished(true)
                .name(addRoleUserDTO.getName())
                .surname(addRoleUserDTO.getSurname())
                .email(addRoleUserDTO.getEmail())
                .systemRoleName(SystemRoleName.valueOf(addRoleUserDTO.getSystemRoleName()))
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .enabled(true)
                .ball(0L)
                .isCredentialsNonExpired(true)
                .password(passwordEncoder.encode(addRoleUserDTO.getPassword()))
                .isFinished(true)
                .groups(groupRepository.findById(addRoleUserDTO.getGroupId()).orElseThrow(() -> new NotFoundException("gruppa topilmadi")))
                .build();
    }
}
