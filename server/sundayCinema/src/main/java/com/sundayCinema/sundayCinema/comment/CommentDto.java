package com.sundayCinema.sundayCinema.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentDto {

    public static class CommentResponseDto {
        private long commentId;
        private String content;
        private int rank;

        //  private long movieId;
        //  private String nickname;

        /*
        movieId/memberNickname 설정 필요
        */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    public static class CommentPostDto {
        private String content;
        private int rank;
    }

    @Getter
    @Setter
    public static class CommentPatchDto {
        private long CommentId;
        private String content;
        private int rank;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }

}
