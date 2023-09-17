package com.sundayCinema.sundayCinema.member;



import com.sundayCinema.sundayCinema.advice.audit.Auditable;
import com.sundayCinema.sundayCinema.comment.Comment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column
    private String userName;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Column
    private String profileImageUrl;

    public Member(String email) {
        this.email = email;
    }

    public Member(String userName, String email,
                  String password,
                  List<String> roles,
                  String profileImageUrl
             ) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profileImageUrl = profileImageUrl;
    }

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

}
