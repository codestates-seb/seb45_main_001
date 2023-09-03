//package com.sundayCinema.sundayCinema.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@RestController
//@RequestMapping("/api/auth")
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
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
//                                              HttpServletRequest request) {
//
//        // 사용자 인증 로직...
//
//        // 인증 처리
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );
//
//        // 세션 아이디 생성
//        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//
//        // 로그인 응답에 세션 아이디 추가
//        return ResponseEntity.ok(new LoginResponse(true, "Login success", sessionId));
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
//        // 사용자 회원가입 로직 구현
//
//        // 사용자 객체 생성
//        User user = new User();
//        user.setUsername(signUpRequest.getUsername());
//        user.setEmail(signUpRequest.getEmail());
//
//        // 비밀번호를 암호화하여 저장
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//        // 사용자 정보 저장
//        userRepository.save(user);
//
//        return new ResponseEntity<>(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);
//    }
//
//
//}
//
//
