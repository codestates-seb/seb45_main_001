package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.movie.dto.MovieDto;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final MovieRepository movieRepository;

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(MovieRepository movieRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.movieRepository = movieRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public List<CommentDto.CommentResponseDto> getCommentsByMovieId(Long movieId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> commentPage = commentRepository.findByMovieMovieId(movieId, pageable);

        List<CommentDto.CommentResponseDto> commentResponseDtos = commentPage.getContent().stream()
                .map(commentMapper::commentToCommentResponseDto)
                .collect(Collectors.toList());

        return commentResponseDtos;
    }

    public CommentDto.CommentResponseDto createComment(CommentDto.CommentPostDto commentPostDto) {
        Comment comment = commentMapper.commentPostDtoToComment(commentPostDto);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.commentToCommentResponseDto(savedComment);
    }

    public CommentDto.CommentResponseDto updateComment(CommentDto.CommentPatchDto commentPatchDto) {
        // 1. commentRepository를 사용하여 댓글을 가져옵니다.
        Optional<Comment> commentOptional = commentRepository.findById(commentPatchDto.getCommentId());

        // 2. 댓글이 존재하는지 확인하고 업데이트합니다.
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // 3. 댓글의 내용, 평점, 수정 시간을 업데이트합니다.
            comment.setContent(commentPatchDto.getContent());
            comment.setScore(commentPatchDto.getScore());
            comment.setModifiedAt(commentPatchDto.getModifiedAt());

            // 4. 업데이트된 댓글을 데이터베이스에 저장합니다.
            Comment updatedComment = commentRepository.save(comment);

            // 5. 업데이트된 댓글을 CommentDto로 매핑하여 반환합니다.
            return commentMapper.commentToCommentResponseDto(updatedComment);
        } else {
            // 댓글이 존재하지 않는 경우 예외 처리 또는 적절한 반환 처리를 수행할 수 있습니다.
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다: " + commentPatchDto.getCommentId());
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public double getAverageScoreForMovie(Long movieId) {
        List<Comment> comments = commentRepository.findByMovieMovieId(movieId);
        if (comments.isEmpty()) {
            return 0.0; // 혹은 원하는 기본값을 반환할 수 있습니다.
        }

        int totalScore = 0;
        for (Comment comment : comments) {
            totalScore += comment.getScore();
        }

        return (double) totalScore / comments.size();
    }
}








    /*
    1. 영화id로 전체 댓글 조회 기능(페이지네이션)
    2. 댓글 작성 기능
    3. 댓글 수정 기능
    4. 댓글 삭제 기능


    * 추후 구현할 기능
    ㄱ. 대댓글 기능
    ㄴ. 회원이 작성한 전체 댓글 조회 기능
     */

