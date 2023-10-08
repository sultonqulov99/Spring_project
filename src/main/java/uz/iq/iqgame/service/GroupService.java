package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.Groups;
import uz.iq.iqgame.payload.ApiResponse;

import java.util.List;

public interface GroupService {
    ResponseEntity<ApiResponse> addGroups(Groups groups);

    List<Groups> getAllGroups();

    Groups getGroupsById(Long groupId);

    ResponseEntity<ApiResponse> updateGroup(Long groupId , Groups groups);

    ResponseEntity<ApiResponse> deleteGroups(Long groupId);
}
