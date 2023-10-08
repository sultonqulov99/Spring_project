package uz.iq.iqgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.UserOwnDataDTO;
import uz.iq.iqgame.payload.UserUpdateDTO;
import uz.iq.iqgame.service.UserService;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @GetMapping("/getOwnInformation")
    public ResponseEntity<UserOwnDataDTO> getOwnInformation() {
        return userService.getOwnInformatioin();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateOwnInformation")
    public ResponseEntity<ApiResponse> updateOwnInformation(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateOwnInformation(userUpdateDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/setIsFinished")
    public ResponseEntity<ApiResponse> setIsFinished() {
        return userService.setIsFinished();
    }


}
