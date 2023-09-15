package com.sundayCinema.sundayCinema.logIn.utils;

import com.sundayCinema.sundayCinema.exception.BusinessLogicException;
import com.sundayCinema.sundayCinema.exception.ExceptionCode;
import com.sundayCinema.sundayCinema.logIn.jwt.jwt.JwtTokenizer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class UserAuthService {

    private final JwtTokenizer jwtTokenizer;

    public UserAuthService(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    public String getSignedInUserEmail(HttpServletRequest request) {
        if(request.getHeader("Authorization")==null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }
        String jws = request.getHeader("Authorization");

        if (jws.startsWith("Bearer ")) {
            jws = jws.substring(7);
        }

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
        String email = (String) claims.get("email");
        if (email == null) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }
        return email;
    }
    public String getSignedInUserEmail(HttpServletResponse response) {

        String jws = response.getHeader("Authorization");
        if (jws.startsWith("Bearer ")) {
            jws = jws.substring(7);
        }

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
        String email = (String) claims.get("email");
        if (email == null) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }
        return email;
    }
}
