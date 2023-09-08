package com.sundayCinema.sundayCinema.security.repository;

import com.sundayCinema.sundayCinema.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
