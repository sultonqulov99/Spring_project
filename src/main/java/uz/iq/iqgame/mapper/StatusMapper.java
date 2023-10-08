package uz.iq.iqgame.mapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Status;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.StatusDTO;
import uz.iq.iqgame.repository.GroupRepository;
import uz.iq.iqgame.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class StatusMapper {
    private final GroupRepository groupRepository;


    public StatusDTO toDTO(Status status) {
        return StatusDTO.builder()
                .name(status.getName())
                .id(status.getId())
                .timeExpired(status.getTimeExpired())
                .groupId(status.getId())
                .build();
    }

    public Status toEntity(StatusDTO statusDTO) {
        return Status.builder().name(statusDTO.getName()).timeExpired(statusDTO.getTimeExpired()).groups(groupRepository.findById(statusDTO.getGroupId()).orElseThrow(() -> new NotFoundException("Bunday id li group topilmadi"))).build();
    }
}
