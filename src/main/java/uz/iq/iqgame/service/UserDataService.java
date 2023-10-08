package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.payload.*;

import java.util.List;

public interface UserDataService {
    ResponseEntity<List<UserRatingDTO>> userRating(Long statusId, Integer page, Integer size);
    ResponseEntity<List<OwnDataDTO>> getOwnData();
    ResponseEntity<ApiResponse> addUserData(UserDataForSave userDataForSave);
}
