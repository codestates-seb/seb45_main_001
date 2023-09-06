package com.sundayCinema.sundayCinema.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@AllArgsConstructor
public class CommentDto {

    @Getter
    @Setter
    public static class CommentPostDto {
        private long CommentId;
        private String content;
        private int score;
        @ManyToOne   //
        @JoinColumn(name = "MOVIE_ID")
        private Movie movie;
    }

    @Getter
    @Setter
    public static class CommentPatchDto {
        private long CommentId;
        private String content;
        private int score;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;

    }
    @Getter
    @Setter
    public static class CommentResponseDto {
        private long commentId;
        private String content;
        private int score;

        //  private String nickname;

        /*
        movieId/memberNickname 설정 필요
        */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }

}
