package uz.iq.iqgame.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.iq.iqgame.entity.User;
import uz.iq.iqgame.entity.enums.SystemRoleName;
import uz.iq.iqgame.exception.AuthException;
import uz.iq.iqgame.exception.MainException;
import uz.iq.iqgame.jwt.JwtProvider;
import uz.iq.iqgame.mapper.UserMapper;
import uz.iq.iqgame.payload.ApiResponse;
import uz.iq.iqgame.payload.LoginDTO;
import uz.iq.iqgame.payload.RegisterDTO;
import uz.iq.iqgame.repository.UserRepository;
import uz.iq.iqgame.service.AuthenticationService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<ApiResponse> adminAuth(LoginDTO loginDTO) {
        User userbek = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new AuthException("Parol yoki email xato"));

        if (userbek.getSystemRoleName().equals(SystemRoleName.ROLE_USER))
            throw new AuthException("Parol yoki email xato");
        if (!passwordEncoder.matches(loginDTO.getPassword(), userbek.getPassword()))
            throw new AuthException("Parol yoki email xato");

        String token = jwtProvider.generateToken(userbek.getEmail());
        return ResponseEntity.ok(ApiResponse.builder()
                .object(token)
                .message("Admin Paneliga Xush Kelibsiz")
                .isSuccess(true)
                .status(200)
                .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(RegisterDTO registerDTO) {
        if (userRepository.existsByEmailAndEnabledFalse(registerDTO.getEmail())) {
            User user = userRepository.findByEmail(registerDTO.getEmail()).get();
            user.setEmailCode(String.valueOf(new Random().nextInt(9999)));
            User savedUser = userRepository.save(user);
            if (sendEmail(savedUser.getEmail(), savedUser.getEmailCode()))
                return ResponseEntity.ok(ApiResponse.builder().status(200).message("Emailingizga kod jonatildi tasdiqlang").isSuccess(true).build());

            throw new MainException("!!! Ko'd yuborishda xatolik yuzaga keldi");
        }

        if (userRepository.existsByEmailAndEnabledTrue(registerDTO.getEmail()))
            throw new MainException("Bunday emailli user allaqachon ro'yxatdan o'tgan");
        User user = userMapper.toEntity(registerDTO);
        user.setEmailCode(String.valueOf(new Random().nextInt(9999)));
        user.setIsFinished(false);
        userRepository.save(user);
        if (sendEmail(user.getEmail(), user.getEmailCode()))
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder().status(200).message("Emailingizga tasdiqlash kodi yuborildi ").isSuccess(true).build());

        throw new MainException("!!! Ko'd yuborishda xatolik yuzaga keldi");
    }


    private boolean sendEmail(String email, String emailCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("ItLiveCompany@gmail.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Tasdiqlash kodi");
            simpleMailMessage.setText(emailCode);
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> loginUser(LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            User user = (User) authenticate.getPrincipal();
            if (!user.getSystemRoleName().name().equals("ROLE_USER")) {
                throw new AuthException("Email yoki password Xato");
            }
            String token = jwtProvider.generateToken(user.getUsername());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.builder()
                    .isSuccess(true)
                    .status(200)
                    .message("Tizimga xush kelibsiz")
                    .object(token)
                    .build());
        } catch (Exception e) {
            throw new AuthException("Email yoki password Xato");
        }

    }

    @Override
    public ResponseEntity<ApiResponse> verifyEmail(String emailCode, String email) {
        User user = userRepository.findByEmailAndEmailCode(email, emailCode).orElseThrow(() -> new AuthException("Email yoki passwordda xatolik mavjud. emailingizni tekshiring"));
        user.setEmailCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        String token = jwtProvider.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .isSuccess(true)
                .message("Tasdiqlandi")
                .status(201)
                .object(token).build()
        );
    }


}
