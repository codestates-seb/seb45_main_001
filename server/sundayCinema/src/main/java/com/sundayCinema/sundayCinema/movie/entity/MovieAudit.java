package com.sundayCinema.sundayCinema.movie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MOVIE_AUDIT")
public class MovieAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AuditId;
    @Column
    private String auditNo;
    @Column
    private String watchGradeNm;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
