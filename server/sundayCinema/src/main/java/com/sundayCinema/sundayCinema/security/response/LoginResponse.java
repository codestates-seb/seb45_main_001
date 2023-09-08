<<<<<<< HEAD:server/sundayCinema/src/main/java/com/sundayCinema/sundayCinema/security/response/LoginResponse.java
package com.sundayCinema.sundayCinema.security.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Boolean success;
    private String message;

    private String sessionId; // 세션 아이디

    public LoginResponse(Boolean success, String message, String sessionId) {
        this.success = success;
        this.message = message;
        this.sessionId = sessionId;
    }
}
=======
//package com.sundayCinema.sundayCinema.test;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class LoginResponse {
//    private Boolean success;
//    private String message;
//    private String sessionId; // 세션 아이디
//
//    public LoginResponse(Boolean success, String message, String sessionId) {
//        this.success = success;
//        this.message = message;
//        this.sessionId = sessionId;
//    }
//}
>>>>>>> a8a0389c2ebfab9d514e616169e240966ee9b6a8:server/sundayCinema/src/main/java/com/sundayCinema/sundayCinema/test/LoginResponse.java
