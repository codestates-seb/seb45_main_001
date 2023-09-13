package com.sundayCinema.sundayCinema.security.controller;

import com.sundayCinema.sundayCinema.security.dto.LoginRequest;
import com.sundayCinema.sundayCinema.security.dto.SignUpRequest;
import com.sundayCinema.sundayCinema.security.entity.User;
import com.sundayCinema.sundayCinema.security.repository.SessionRepository;
import com.sundayCinema.sundayCinema.security.repository.UserRepository;
import com.sundayCinema.sundayCinema.security.response.ApiResponse;
import com.sundayCinema.sundayCinema.security.response.LoginResponse;
import com.sundayCinema.sundayCinema.security.security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/host")
public class AuthController {

    //    @Autowired //이거 안쓰고 해보기
    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private PasswordEncoder passwordEncoder;
//    private final AuthenticationService authenticationService;

    private String getNicknameFromDatabase(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getNickname();
        }
        return null; // 사용자가 없을 경우에 대한 처리
    }

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
//public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
//                      PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
//    this.authenticationManager = authenticationManager;
//    this.userRepository = userRepository;
//    this.passwordEncoder = passwordEncoder;
//    this.authenticationService = authenticationService;
//}

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
//                                              HttpServletRequest request, HttpServletResponse response) {
//
//        // 사용자 인증 로직...

//        // 인증 처리
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//        );
//        // 회원의 닉네임 가져오기 (예: userRepository.findByEmail(loginRequest.getEmail()).getNickname())
//        String nickname = getNicknameFromDatabase(loginRequest.getEmail());
//
//        // 세션 아이디 생성 (선택 사항)
//
//        // 쿠키 생성 및 추가
//        Cookie cookie = new Cookie("sessionId", "your-session-id");
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
////
////        Cookie cookie1 = new Cookie("cookieName1", "cookieValue1");
////        cookie.setPath("/");
////        response.addCookie(cookie1); // 응답에 쿠키 추가
////
////
////        Cookie cookie2 = new Cookie("cookieName2", "cookieValue2");
////        cookie.setPath("/");
////        response.addCookie(cookie2);
////
////        Cookie cookie3 = new Cookie("cookieName3", "cookieValue3");
////        cookie.setPath("/");
////        response.addCookie(cookie3); // 응답에 쿠키 추가
////
////
////        Cookie cookie4 = new Cookie("cookieName4", "cookieValue4");
////        cookie.setPath("/");
////        response.addCookie(cookie4);
//
//
//        // 로그인 응답에 세션 아이디 추가 (선택 사항)
//        // ...
//
//        // 로그인 응답에 쿠키 정보 및 기타 데이터 전달
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("success", true);
//        responseMap.put("message", "로그인이 성공적으로 완료되었습니다.");
//        responseMap.put("nickname", nickname);
//
//        return ResponseEntity.ok(responseMap);
//    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletRequest request, HttpServletResponse response) {

        // 사용자 인증 로직...

        // 인증 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        // 회원의 닉네임 가져오기 (예: userRepository.findByEmail(loginRequest.getEmail()).getNickname())
        String nickname = getNicknameFromDatabase(loginRequest.getEmail());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 세션 아이디 생성
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        Cookie sessionCookie = new Cookie("sessionId", sessionId);
        sessionCookie.setPath("/"); // 쿠키의 경로 설정 (루트 경로에 대해 모든 요청에 적용)
        response.addCookie(sessionCookie);


        // 로그인 응답에 세션 아이디 추가
        return ResponseEntity.ok(new LoginResponse(true, "로그인이 성공적으로 완료되었습니다.", nickname, sessionId));
    }

    //
//
//        Cookie cookie = new Cookie("SESSION_COOKIE_NAME", sessionId);
//        cookie.setPath("/"); // 경로 설정
//        cookie.setHttpOnly(false);
//        // 쿠키를 클라이언트에게 전송
//        response.addCookie(cookie);
//
//
//        return ResponseEntity.ok(loginResponse);
//    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        // 사용자 회원가입 로직 구현

        // User 객체 생성 및 초기화
        User user = new User.Builder()
                .setNickname(signUpRequest.getNickname())
                .setEmail(signUpRequest.getEmail())
                .setPassword(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

//        // 사용자 객체 생성
//        User user = new User(); // 빌드 패턴으로 바꿔보자
//        user.setNickname(signUpRequest.getNickname());
//        user.setEmail(signUpRequest.getEmail());
//
//        // 비밀번호를 암호화하여 저장
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // encode 스레드가 여려개 만들어지는지, 세이브를 여러개 하는지 확인하는지

        // 사용자 정보 저장
        userRepository.save(user);
        log.info("user :" + user.getEmail());
        return new ResponseEntity<>(new ApiResponse(true, "회원가입이 성공적으로 완료되었습니다"), HttpStatus.CREATED);
    }


    @GetMapping("/check-session")
//    public ResponseEntity<String> checkSession(HttpServletRequest request) {
//        // 클라이언트에서 전달한 세션 아이디를 쿠키에서 가져옵니다.
//        Cookie[] cookies = request.getCookies();
//        String sessionId = null;
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("sessionId".equals(cookie.getName())) { // 쿠키 이름에 따라 변경
//                    sessionId = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        // 세션 아이디의 유효성을 검사합니다.
//        if (sessionId != null && sessionRepository.isValidSession(sessionId)) {
//            // 세션 아이디가 유효한 경우, 사용자 인증 및 작업 수행
//            // 이 부분에서 사용자 인증 및 작업을 수행하면 됩니다.
//            // 예를 들어, 사용자 정보를 가져오거나 작업을 수행합니다.
//            String username = "authenticatedUser"; // 사용자 이름을 가져오는 예시
//
//            return ResponseEntity.ok("Authenticated User: " + username);
//        } else {
//            // 세션 아이디가 유효하지 않은 경우, 에러 또는 다른 처리를 수행합니다.
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//        }
//    }
//}
//    public String checkSession() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                String nickname = userDetails.getUsername();
//                return "true";
//            }
//        }
//        return "false";
//    }
//}
//    public String checkSession(HttpServletRequest request) {
//        HttpSession session = request.getSession(false); // false를 사용하여 새 세션을 생성하지 않도록 설정
//
//        if (session != null) {
//            String sessionId = session.getId();
//            return sessionId;
//        }
//
//        return "No session found";
//    }
//
//}
//    public String checkSession() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("로그인 상태1 : " + authentication.isAuthenticated());
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            log.info("로그인 상태2 : " + principal.toString());
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                log.info("로그인 상태2 : " + userDetails.getUsername());
//                String nickname = userDetails.getUsername();
//                return "현재 사용자 " + nickname + "은(는) 로그인되어 있습니다.";
//            }
//        }
//
//        return "현재 사용자는 로그인되어 있지 않습니다.";
//    }
//    public ResponseEntity<String> checkSession(@RequestHeader("Custom-Header") String customHeader) {
//        // customHeader 값을 읽어와서 사용
//        return ResponseEntity.ok("Received Custom-Header: " + customHeader);
//    }
    public ResponseEntity<String> checkSession(@RequestHeader("Session-Id") String sessionId) {
        if (sessionId == null ) {
            // 세션 아이디가 없거나 비어 있는 경우, 에러 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Session-Id is missing");
        }

        if (sessionRepository.isValidSession(sessionId)) {
            // 세션 아이디가 유효한 경우, 사용자 인증 및 작업 수행
            // 이 부분에서 사용자 인증 및 작업을 수행하면 됩니다.
            // 예를 들어, 사용자 정보를 가져오거나 작업을 수행합니다.
            String nickname = "authenticatedUser"; // 사용자 이름을 가져오는 예시

            return ResponseEntity.ok("Authenticated User: " + nickname);
        } else {
            // 세션 아이디가 유효하지 않은 경우, 에러 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid Session-Id");
        }
    }
}
//
//    @GetMapping("/check")
//    public String check(HttpServletRequest request) {
//        return authenticationService.loginCheck(request);
//    }
//}

