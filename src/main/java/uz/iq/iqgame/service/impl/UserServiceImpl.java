package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.exception.AuthException;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.mapper.UserOwnDataMapper;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.UserOwnDataDTO;
import uz.iq.iqgame.payload.UserUpdateDTO;
import uz.iq.iqgame.repository.GroupRepository;
import uz.iq.iqgame.repository.StatusRepository;
import uz.iq.iqgame.repository.UserDataRepository;
import uz.iq.iqgame.repository.UserRepository;
import uz.iq.iqgame.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserOwnDataMapper userOwnDataMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final UserDataRepository userDataRepository;
    private final GroupRepository groupRepository;

    @Override

    public ResponseEntity<UserOwnDataDTO> getOwnInformatioin() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userOwnDataMapper.toDTO(currentUser));
    }

    @Override
    public ResponseEntity<ApiResponse> updateOwnInformation(UserUpdateDTO userUpdateDTO) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(userUpdateDTO.getOldPassword(), currentUser.getPassword()))
            throw new AuthException("Eski parolingiz xato");
        currentUser.setName(userUpdateDTO.getName());
        currentUser.setSurname(userUpdateDTO.getSurname());
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().equals("") && !userUpdateDTO.getPassword().equals(" "))
            currentUser.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));

        if (!currentUser.getGroups().getId().equals(userUpdateDTO.getGroupsId())) {
            if (!statusRepository.existsByGroupsIdAndId(userUpdateDTO.getGroupsId(), userUpdateDTO.getStatusId()))
                throw new MainException("Tanlagan statusingiz ushbu gruppaga tegishli emas");

            currentUser.setBall(0L);
            currentUser.setGroups(groupRepository.findById(userUpdateDTO.getGroupsId()).orElseThrow(() -> new NotFoundException("Group topilmadi")));
            currentUser.setStatus(statusRepository.findById(userUpdateDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Status topilmadi")));
            currentUser.setBall(0L);
            userDataRepository.deleteUserDataByUser(userRepository.findById(currentUser.getId()).orElseThrow(() -> new NotFoundException("User topilmadi")));
        }


        userRepository.save(currentUser);
        return ResponseEntity.ok(ApiResponse.builder().status(200).isSuccess(true).message("Malumotlar o'zgartirildi")
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse> setIsFinished() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setIsFinished(true);
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Siz savollarga javob berdingiz (vaqt)")
                .isSuccess(true)
                .status(200)
                .build()
        );
    }
}
