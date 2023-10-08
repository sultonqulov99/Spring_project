package uz.iq.iqgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.entity.Groups;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addGroup")
    public ResponseEntity<ApiResponse> addGroup(@RequestBody Groups groups) {
        return groupService.addGroups(groups);
    }

    @GetMapping("/getAllGroups")
    public List<Groups> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/getGroupById/{groupId}")
    public Groups getGroupById(@PathVariable Long groupId) {
        return groupService.getGroupsById(groupId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateGroup/{groupId}")
    public ResponseEntity<ApiResponse> updateGroup(@PathVariable Long groupId, @RequestBody Groups groups) {
        return groupService.updateGroup(groupId, groups);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<ApiResponse> deleteGroup(@PathVariable Long groupId) {
        return groupService.deleteGroups(groupId);
    }
}
