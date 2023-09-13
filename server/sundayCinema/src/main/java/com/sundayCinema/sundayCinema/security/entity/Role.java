package com.sundayCinema.sundayCinema.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(unique = true)
    private String name;

    // Getter와 Setter 메서드 추가
}
