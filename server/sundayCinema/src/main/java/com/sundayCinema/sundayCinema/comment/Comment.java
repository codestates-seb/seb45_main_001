package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.advice.audit.Auditable;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private int score;

    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;



    //adsfdasf
    // movieId 연관관계 설정 필요

}
