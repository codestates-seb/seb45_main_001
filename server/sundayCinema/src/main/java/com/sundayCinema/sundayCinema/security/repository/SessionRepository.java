package com.sundayCinema.sundayCinema.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addSession(String sessionId) {
        jdbcTemplate.update("INSERT INTO sessions (session_id) VALUES (?)", sessionId);
    }

    public boolean isValidSession(String sessionId) {
        if (sessionId == null) {
            return false; // sessionId가 null이면 세션이 유효하지 않음
        }
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sessions WHERE session_id = ?", Integer.class, sessionId);
        return count > 0;
    }
    }

    // 세션 아이디의 유효 기간이 지나면 삭제하는 로직도 필요할 수 있습니다.

