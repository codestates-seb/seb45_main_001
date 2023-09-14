package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.member.Member;
import com.sundayCinema.sundayCinema.member.MemberRepository;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam("movieId") long movieId) {
        // Fetch User and Movie objects based on userId and movieId
        Member member = memberRepository.findById(memberId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (member == null || movie == null) {
            // 사용자 또는 영화가 존재하지 않는 경우 처리
            return ResponseEntity.badRequest().build();
        }

        CommentDto.CommentResponseDto response = commentService.createComment(commentPostDto, member, movie);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto.CommentResponseDto> updateComment(
            @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentDto.CommentPatchDto commentPatchDto) {
        commentPatchDto.setCommentId(commentId);
        CommentDto.CommentResponseDto response = commentService.updateComment(commentPatchDto);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<CommentDto.CommentResponseDto>> getCommentsForMovie(@PathVariable("movieId") long movieId) {
        List<CommentDto.CommentResponseDto> comments = commentService.getCommentsForMovie(movieId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") long commentId) {
        if (commentService.deleteComment(commentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/movie/{movieId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForMovie(@PathVariable("movieId") long movieId) {
        double averageRating = commentService.calculateAverageRatingForMovie(movieId);
        return ResponseEntity.ok(averageRating);
    }
}