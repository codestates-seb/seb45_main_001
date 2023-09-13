package com.sundayCinema.sundayCinema.security.security;

import com.sundayCinema.sundayCinema.security.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void addSession(String sessionId) {
        sessionRepository.addSession(sessionId);
    }


    public boolean isValidSession(String sessionId) {
        return sessionRepository.isValidSession(sessionId);
    }
}
