package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.movie.Service.MovieService;
import com.sundayCinema.sundayCinema.movie.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    private final MovieService movieService;

    @Autowired
    public CommentController(CommentService commentService, MovieService movieService) {
        this.commentService = commentService;
        this.movieService = movieService;
    }



    @GetMapping("/{movieId}")
    public ResponseEntity<List<CommentDto.CommentResponseDto>> getCommentsByMovieId(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<CommentDto.CommentResponseDto> comments = commentService.getCommentsByMovieId(movieId, page, size);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/{movieId}/create")
    public ResponseEntity<CommentDto.CommentResponseDto> createComment(@RequestBody CommentDto.CommentPostDto commentPostDto) {
        CommentDto.CommentResponseDto createdComment = commentService.createComment(commentPostDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{movieId}/{commentId}/update")
    public ResponseEntity<CommentDto.CommentResponseDto> updateComment(@RequestBody CommentDto.CommentPatchDto commentPatchDto) {
        CommentDto.CommentResponseDto updatedComment = commentService.updateComment(commentPatchDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    @DeleteMapping("/{movieId}/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long movieId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{movieId}/average-score")
    public ResponseEntity<Double> getAverageScoreForMovie(@PathVariable Long movieId) {
        // CommentService를 호출하여 해당 영화의 평균평점을 가져온 후 반환합니다.
        Double averageScore = commentService.getAverageScoreForMovie(movieId);
        return ResponseEntity.ok(averageScore);
    }
}
      /*
    1. 영화id로 전체 댓글 조회 기능(페이지네이션) "/{movieId}/comments"
    2. 댓글 작성 기능 "/{movieId}/comment"
    3. 댓글 수정 기능 "{movieId}/comment/{commentId}"
    4. 댓글 삭제 기능 "{movieId}/comment/{commentId}"


    * 추후 구현할 기능
    ㄱ. 대댓글 기능
    ㄴ. 회원이 작성한 전체 댓글 조회 기능
     */

