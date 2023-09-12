package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.advice.audit.Auditable;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @Column(nullable = false)
    private int score; // 평점 (1부터 5까지)

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie; // 댓글이 달린 영화

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user; // 댓글을 작성한 사용자



    // 생성자 및 게터/세터는 생략 (이미 작성된 코드와 동일)

    // 이하 평균평점 계산 및 관련 기능 추가 가능
}
