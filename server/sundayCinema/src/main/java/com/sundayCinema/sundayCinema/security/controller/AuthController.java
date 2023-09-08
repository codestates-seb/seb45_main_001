package com.sundayCinema.sundayCinema.security.controller;

import com.sundayCinema.sundayCinema.security.dto.LoginRequest;
import com.sundayCinema.sundayCinema.security.dto.SignUpRequest;
import com.sundayCinema.sundayCinema.security.entity.User;
import com.sundayCinema.sundayCinema.security.repository.UserRepository;
import com.sundayCinema.sundayCinema.security.response.ApiResponse;
import com.sundayCinema.sundayCinema.security.response.LoginResponse;
import com.sundayCinema.sundayCinema.security.security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequestMapping("/membership")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
                                              HttpServletRequest request) {

        // 사용자 인증 로직...

        // 인증 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        log.info("요청 : " + loginRequest.getEmail());
        // 세션 아이디 생성
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        log.info("세션 아이디: " + sessionId);
        // 로그인 응답에 세션 아이디 추가
        return ResponseEntity.ok(new LoginResponse(true, "로그인이 성공적으로 완료되었습니다.", sessionId));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        // 사용자 회원가입 로직 구현

        // 사용자 객체 생성
        User user = new User();
        user.setNickname(signUpRequest.getNickname());
        user.setEmail(signUpRequest.getEmail());
        log.info("회원가입 :" + user.getEmail());
        // 비밀번호를 암호화하여 저장
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // 사용자 정보 저장
        userRepository.save(user);

        log.info("user :" + userRepository.findByEmail(user.getEmail()).getNickname());
        return new ResponseEntity<>(new ApiResponse(true, "회원가입이 성공적으로 완료되었습니다"), HttpStatus.CREATED);
    }


    @GetMapping("/check-session")
    public String checkSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("로그인 상태1 : " + authentication.isAuthenticated());
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            log.info("로그인 상태2 : " + principal.toString());
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                log.info("로그인 상태2 : " + userDetails.getUsername());
                String nickname = userDetails.getUsername();
                return "현재 사용자 " + nickname + "은(는) 로그인되어 있습니다.";
            }
        }

        return "현재 사용자는 로그인되어 있지 않습니다.";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        HttpSession requestSession = request.getSession();
        log.info("requestSession"+requestSession.toString());
        HttpSession authenticationSession = authenticationService.getSession();
        log.info("authenticationSession"+authenticationSession.toString());
        if (requestSession.equals(authenticationSession)) return "현재 사용자는 로그인 상태입니다.";
        else return "현재 사용자는 로그인되어 있지 않습니다.";
    }

}


