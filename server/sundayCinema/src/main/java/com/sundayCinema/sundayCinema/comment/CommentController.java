package com.sundayCinema.sundayCinema.comment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class CommentController {
      /*
    1. 영화id로 전체 댓글 조회 기능(페이지네이션) "/{movieId}/comments"
    2. 댓글 작성 기능 "/{movieId}/comment"
    3. 댓글 수정 기능 "/comment/{commentId}"
    4. 댓글 삭제 기능 "/comment/{commentId}"


    * 추후 구현할 기능
    ㄱ. 대댓글 기능
    ㄴ. 회원이 작성한 전체 댓글 조회 기능
     */
}
