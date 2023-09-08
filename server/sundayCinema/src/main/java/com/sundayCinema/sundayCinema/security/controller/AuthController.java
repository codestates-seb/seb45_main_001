//package com.sundayCinema.sundayCinema.security.controller;
//
//import com.sundayCinema.sundayCinema.security.dto.LoginRequest;
//import com.sundayCinema.sundayCinema.security.dto.SignUpRequest;
//import com.sundayCinema.sundayCinema.security.entity.User;
//import com.sundayCinema.sundayCinema.security.repository.UserRepository;
//import com.sundayCinema.sundayCinema.security.response.ApiResponse;
//import com.sundayCinema.sundayCinema.security.response.LoginResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@RestController
//@Slf4j
//@RequestMapping("/host")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
//                                              HttpServletRequest request) {
//
//        // 사용자 인증 로직...
//
//        // 인증 처리
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//        );
//
//        // 세션 아이디 생성
//        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//
//        // 로그인 응답에 세션 아이디 추가
//        return ResponseEntity.ok(new LoginResponse(true, "로그인이 성공적으로 완료되었습니다.", sessionId));
//    }
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
//        // 사용자 회원가입 로직 구현
//
//        // 사용자 객체 생성
//        User user = new User();
//        user.setNickname(signUpRequest.getNickname());
//        user.setEmail(signUpRequest.getEmail());
//
//        // 비밀번호를 암호화하여 저장
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//        // 사용자 정보 저장
//        userRepository.save(user);
//        log.info("user :" + user.getEmail());
//        return new ResponseEntity<>(new ApiResponse(true, "회원가입이 성공적으로 완료되었습니다"), HttpStatus.CREATED);
//    }
//
//
//
//
//    @GetMapping("/check-session")
//    public String checkSession() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                String nickname = userDetails.getUsername();
//                return "현재 사용자 " + nickname + "은(는) 로그인되어 있습니다.";
//            }
//        }
//
//        return "현재 사용자는 로그인되어 있지 않습니다.";
//    }
//
//}
//
//
