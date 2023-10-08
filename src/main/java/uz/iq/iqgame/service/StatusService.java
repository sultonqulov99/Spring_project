package uz.iq.iqgame.service;


import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.Status;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.StatusDTO;

import java.util.List;

public interface StatusService {
    ResponseEntity<List<StatusDTO>> getAllStatus(Long groupsId);

    ResponseEntity<StatusDTO> getById(Long id);

    ResponseEntity<ApiResponse> addStatus(StatusDTO statusDTO);

    ResponseEntity<ApiResponse> updateStatus(Long id, StatusDTO status);

    ResponseEntity<ApiResponse> deleteStatus(Long id);
}
