package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.Groups;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.exception.NotFoundException;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.repository.GroupRepository;
import uz.iq.iqgame.service.GroupService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public ResponseEntity<ApiResponse> addGroups(Groups groups) {
        if (groupRepository.existsByName(groups.getName()))
            throw new MainException("Bunday nomli group bor");
        groupRepository.save(groups);
        return ResponseEntity.ok(ApiResponse.builder().message("gruppa qo'shildi").status(200).isSuccess(true).build());
    }

    @Override
    public List<Groups> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Groups getGroupsById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Bunday id li gruppa topilmadi"));
    }

    @Override
    public ResponseEntity<ApiResponse> updateGroup(Long groupId, Groups groups) {
        if (groupRepository.existsByName(groups.getName()))
            throw new MainException("Bunday nomli group bor");
        Groups editedGroup = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Gruppa toplmadi"));
        editedGroup.setName(groups.getName());
        groupRepository.save(editedGroup);
        return ResponseEntity.ok(ApiResponse.builder().message("o'chirildi").isSuccess(true).status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGroups(Long groupId) {
        try {
            groupRepository.deleteById(groupId);
            return ResponseEntity.ok(ApiResponse.builder().message("o'chirildi").isSuccess(true).status(200).build());
        } catch (Exception e) {
            throw new MainException("Gruppa o'chirilmadi qandaydir hatolik ketti");
        }
    }
}
