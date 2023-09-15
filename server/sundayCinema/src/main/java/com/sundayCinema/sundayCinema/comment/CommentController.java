package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.member.Member;
import com.sundayCinema.sundayCinema.member.MemberRepository;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    public CommentController(CommentService commentService, MemberRepository memberRepository, MovieRepository movieRepository) {
        this.commentService = commentService;
        this.memberRepository = memberRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public ResponseEntity<CommentDto.CommentResponseDto> createComment(
            @Valid @RequestBody CommentDto.CommentPostDto commentPostDto,
            @RequestParam("memberId") long memberId,
            @RequestParam("movieId") long movieId,
            HttpServletRequest request) {


        CommentDto.CommentResponseDto response = commentService.createComment(commentPostDto, memberId, movieId, request);
        return ResponseEntity.ok(response);
    }


//    @PatchMapping("/{commentId}")
//    public ResponseEntity<CommentDto.CommentResponseDto> updateComment(
//            @PathVariable("commentId") long commentId,  HttpServletRequest request,
//            @Valid @RequestBody CommentDto.CommentPatchDto commentPatchDto, long movieId, long memberId) {
//        commentPatchDto.setCommentId(commentId);
//        CommentDto.CommentResponseDto response = commentService.updateComment(commentPatchDto, memberId, movieId, commentId, request);
//        if (response != null) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/movie/{movieId}")
//    public ResponseEntity<List<CommentDto.CommentResponseDto>> getCommentsForMovie(@PathVariable("movieId") long movieId,HttpServletRequest request, @RequestBody long memberId ) {
//        List<CommentDto.CommentResponseDto> comments = commentService.getCommentsForMovie(movieId, memberId, request);
//        return ResponseEntity.ok(comments);
//    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") long commentId, @RequestBody long movieId, long memberId, HttpServletRequest request) {
//        if (commentService.deleteComment(commentId, movieId, memberId, request)) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/movie/{movieId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForMovie(@PathVariable("movieId") long movieId) {
        double averageRating = commentService.calculateAverageRatingForMovie(movieId);
        return ResponseEntity.ok(averageRating);
    }
}