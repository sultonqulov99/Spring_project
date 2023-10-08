package uz.iq.iqgame.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.StatusDTO;
import uz.iq.iqgame.service.StatusService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;


    @GetMapping("/getAllStatusByGroupId/{groupId}")
    public ResponseEntity<List<StatusDTO>> getAllStatus(@PathVariable Long groupId) {
        return statusService.getAllStatus(groupId);
    }

    @GetMapping("/getStatus/{id}")
    public ResponseEntity<StatusDTO> getById(@PathVariable Long id) {
        return statusService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @PostMapping("/addStatus")
    public ResponseEntity<ApiResponse> addStatus(@Valid @RequestBody StatusDTO statusDTO) {
        return statusService.addStatus(statusDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusDTO status) {
        return statusService.updateStatus(id, status);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'ASISTANT')")
    @DeleteMapping("/deleteStatus/{id}")
    public ResponseEntity<ApiResponse> deleteStatus(@PathVariable Long id) {
        return statusService.deleteStatus(id);
    }
}
