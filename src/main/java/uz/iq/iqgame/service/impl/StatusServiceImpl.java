package uz.iq.iqgame.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import uz.iq.iqgame.entity.Status;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.exception.AuthException;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.mapper.StatusMapper;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.StatusDTO;
import uz.iq.iqgame.repository.GroupRepository;
import uz.iq.iqgame.repository.StatusRepository;
import uz.iq.iqgame.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final GroupRepository groupRepository;

    @Override
    public ResponseEntity<List<StatusDTO>> getAllStatus(Long groupsId) {
        return ResponseEntity.ok(statusRepository.findAllByGroupsId(groupsId).stream().map(statusMapper::toDTO).toList());
    }

    @Override
    public ResponseEntity<StatusDTO> getById(Long id) {
        return ResponseEntity.ok(statusMapper.toDTO(statusRepository.findById(id).orElseThrow(() -> new NotFoundException("Status topilmadi"))));
    }

    @Override
    public ResponseEntity<ApiResponse> addStatus(StatusDTO statusDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!statusDTO.getGroupId().equals(user.getGroups().getId()))
            throw new AuthException("Siz ushbu gruppaga status qo'sha olmaysiz");

        if (statusRepository.existsByName(statusDTO.getName()))
            throw new MainException("Bunday nomli status mavjud");

        statusRepository.save(statusMapper.toEntity(statusDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder().status(200).isSuccess(true).message("Status yaratildi").build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateStatus(Long id, StatusDTO statusDTO) {
        if (statusRepository.existsByName(statusDTO.getName()))
            throw new MainException("Bunday nomli status mavjud");
        Status status1 = statusRepository.findById(id).orElseThrow(() -> new NotFoundException("Status topilmadi"));
        status1.setTimeExpired(statusDTO.getTimeExpired());
        status1.setName(statusDTO.getName());
        statusRepository.save(status1);
        return ResponseEntity.ok(ApiResponse.builder().status(200).isSuccess(true).message("Status o'zgartirildi").build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStatus(Long id) {
        try {
            statusRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.builder().status(200).isSuccess(true).message("Status o'chirildi").build());
        } catch (Exception e) {
            throw new MainException("O'chirishda muammo");
        }
    }
}
