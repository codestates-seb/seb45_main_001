<<<<<<< HEAD:server/sundayCinema/src/main/java/com/sundayCinema/sundayCinema/security/entity/Role.java
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
    private Long id;

    @Column(unique = true)
    private String name;

    // Getter와 Setter 메서드 추가
}
=======
//package com.sundayCinema.sundayCinema.test;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String name;
//
//    // Getter와 Setter 메서드 추가
//}
>>>>>>> a8a0389c2ebfab9d514e616169e240966ee9b6a8:server/sundayCinema/src/main/java/com/sundayCinema/sundayCinema/test/Role.java
