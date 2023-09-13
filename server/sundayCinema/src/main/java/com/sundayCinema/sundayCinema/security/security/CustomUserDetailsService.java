package com.sundayCinema.sundayCinema.security.security;


import com.sundayCinema.sundayCinema.security.entity.User;
import com.sundayCinema.sundayCinema.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 사용자를 데이터베이스에서 찾거나 다른 곳에서 사용자 정보를 가져옵니다.
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 사용자 정보를 UserDetails 인터페이스를 구현한 객체로 변환하여 반환합니다.
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.emptyList());

        return userDetails;
    }
}
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));


//    private Set<GrantedAuthority> getAuthorities(Set<Role> roles) {
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }
