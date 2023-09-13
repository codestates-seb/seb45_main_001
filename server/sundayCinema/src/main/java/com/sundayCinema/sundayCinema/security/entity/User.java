package com.sundayCinema.sundayCinema.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    private String nickname;

    private String email;

    private String password;

        public static class Builder {
            private String nickname;
            private String email;
            private String password;

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User();
            user.nickname = this.nickname;
            user.email = this.email;
            user.password = this.password;
            return user;
        }
    }



//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles = new HashSet<>();
//
//    // Getter와 Setter 메서드 추가
}