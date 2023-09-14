package com.sundayCinema.sundayCinema.comment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class CommentDto {

    @Getter
    @Setter
    public static class CommentPostDto {
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        private String content;



        @NotNull(message = "평점은 필수 입력 항목입니다.")
        private double score;

        @NotNull(message = "영화 ID는 필수 입력 항목입니다.")
        private long movieId;

        @NotNull(message = "사용자 ID는 필수 입력 항목입니다.")
        private long memberId;
    }

    @Getter
    @Setter
    public static class CommentPatchDto {
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        private String content;

        private long commentId;

        @NotNull(message = "평점은 필수 입력 항목입니다.")
        private double score;
    }
    @Getter
    @Setter
    public static class CommentResponseDto{
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        private String content;

        private long commentId;

        @NotNull(message = "평점은 필수 입력 항목입니다.")
        private double score;
    }
}
