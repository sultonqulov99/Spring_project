package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.UserOwnDataDTO;
import uz.iq.iqgame.payload.UserUpdateDTO;

public interface UserService {
    ResponseEntity<UserOwnDataDTO> getOwnInformatioin();


    ResponseEntity<ApiResponse> updateOwnInformation(UserUpdateDTO userUpdateDTO);

    ResponseEntity<ApiResponse> setIsFinished();

}
