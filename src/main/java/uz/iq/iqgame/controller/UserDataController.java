package uz.iq.iqgame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.iq.iqgame.payload.*;
import uz.iq.iqgame.service.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userdata")
@RequiredArgsConstructor
public class UserDataController {
    private final UserDataService userDataService;
    @GetMapping("/userRating/{statusId}")
    public ResponseEntity<List<UserRatingDTO>> userRating(@PathVariable Long statusId, @RequestParam Integer page, @RequestParam Integer size){
        return userDataService.userRating(statusId,page,size);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getOwnData")
    public ResponseEntity<List<OwnDataDTO>> getOwnData(){
        return userDataService.getOwnData();
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addUserData")
    public ResponseEntity<ApiResponse> addUserDate(@RequestBody UserDataForSave userDataForSave) {
        return userDataService.addUserData(userDataForSave);
    }
}
