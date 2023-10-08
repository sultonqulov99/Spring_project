package uz.iq.iqgame.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.LoginDTO;
import uz.iq.iqgame.payload.RegisterDTO;

public interface AuthenticationService extends UserDetailsService {
    ResponseEntity<ApiResponse> adminAuth(LoginDTO loginDTO);

    ResponseEntity<ApiResponse> registerUser(RegisterDTO registerDTO);

    ResponseEntity<ApiResponse> loginUser(LoginDTO loginDTO);

    ResponseEntity<ApiResponse> verifyEmail(String emailCode, String email);
}
