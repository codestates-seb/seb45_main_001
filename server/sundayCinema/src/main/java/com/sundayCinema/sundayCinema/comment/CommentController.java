package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.member.Member;
import com.sundayCinema.sundayCinema.member.MemberRepository;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto.CommentResponseDto> createComment(
            @Valid @RequestBody CommentDto.CommentPostDto commentPostDto,
            @RequestParam("memberId") long memberId,
            @RequestParam("movieId") long movieId,
            HttpServletRequest request) {


        CommentDto.CommentResponseDto response = commentService.createComment(commentPostDto, memberId,movieId, request);


        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto.CommentResponseDto> updateComment(
            @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentDto.CommentPatchDto commentPatchDto,
            @RequestParam(name = "memberId", required = true, defaultValue = "0") long memberId,
            @RequestParam(name = "movieId", required = true, defaultValue = "0") long movieId,
            HttpServletRequest request) {
        commentPatchDto.setCommentId(commentId);

        CommentDto.CommentResponseDto response = commentService.updateComment(commentPatchDto, memberId, movieId, request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/movie/{movieId}/{memberId}")
    public ResponseEntity<?> getCommentsForMovie(
            @PathVariable("movieId") Long movieId,
            @PathVariable(name = "memberId", required = false) Long memberId,
            HttpServletRequest request) {

        // movieId와 memberId가 null이면 BadRequest로 처리
        if (movieId == null || memberId == null) {
            return ResponseEntity.badRequest().body("movieId and memberId must not be null.");
        }

       CommentDto.CommentResponseDto comment = commentService.getCommentsForMovie(movieId, memberId, request);
        return ResponseEntity.ok(comment);
    }
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getAllCommentsForMovie(
            @PathVariable("movieId") Long movieId
            ) {

        // movieId와 memberId가 null이면 BadRequest로 처리
        if (movieId == null) {
            return ResponseEntity.badRequest().body("movieId must not be null.");
        }

        List<CommentDto.CommentResponseDto> comments = commentService.getAllCommentsForMovie(movieId);
        return ResponseEntity.ok(comments);
    }




    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") long commentId,
                                              @RequestParam(name = "memberId", required = true, defaultValue = "0") long memberId,
                                              @RequestParam(name = "movieId", required = true, defaultValue = "0") long movieId, HttpServletRequest request) {
        if (commentService.deleteComment(commentId, memberId, movieId, request)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/movie/{movieId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForMovie(@PathVariable("movieId") Long movieId
                                                           ) {
        // movieId와 memberId가 null이면 Bad Request로 처리
        if (movieId == null) {
            return ResponseEntity.badRequest().build();
        }

        double averageRating = commentService.calculateAverageRatingForMovie(movieId);
        return ResponseEntity.ok(averageRating);
    }


}