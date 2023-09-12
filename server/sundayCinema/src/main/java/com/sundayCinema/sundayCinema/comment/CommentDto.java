package com.sundayCinema.sundayCinema.comment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class CommentDto {

    @Getter
    @Setter
    public static class CommentPostDto {
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        @Size(max = 500, message = "댓글 내용은 최대 500자여야 합니다.")
        private String content;

        @NotNull(message = "평점은 필수 입력 항목입니다.")
        private int score;

        @NotNull(message = "영화 ID는 필수 입력 항목입니다.")
        private long movieId;

        @NotNull(message = "사용자 ID는 필수 입력 항목입니다.")
        private long Id;
    }

    @Getter
    @Setter
    public static class CommentPatchDto {
        @Size(max = 500, message = "댓글 내용은 최대 500자여야 합니다.")
        private String content;

        private long commentId;

        private int score;
    }
    @Getter
    @Setter
    public static class CommentResponseDto{
        @Size(max = 500, message = "댓글 내용은 최대 500자여야 합니다.")
        private String content;

        private long commentId;

        private int score;
    }
}
