package com.sundayCinema.sundayCinema.comment;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    // Create a new comment
    public CommentDto.CommentResponseDto createComment(CommentDto.CommentPostDto commentPostDto, User user, Movie movie) {
        Comment comment = commentMapper.commentPostDtoToComment(commentPostDto);
        comment.setUser(user);
        comment.setMovie(movie);
        comment = commentRepository.save(comment);
        CommentDto.CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(comment);
        return commentResponseDto;
    }

    // Update an existing comment
    public CommentDto.CommentResponseDto updateComment(CommentDto.CommentPatchDto commentPatchDto) {
        Comment comment = commentRepository.findById(commentPatchDto.getCommentId()).orElse(null);
        if (comment != null) {
            // Update the comment fields as needed
            if (commentPatchDto.getContent() != null) {
                comment.setContent(commentPatchDto.getContent());
            }
            if (commentPatchDto.getScore() > 0) {
                comment.setScore(commentPatchDto.getScore());
            }
            comment = commentRepository.save(comment);
            return commentMapper.commentToCommentResponseDto(comment);
        }
        return null;
    }

    // Get comments for a movie
    public List<CommentDto.CommentResponseDto> getCommentsForMovie(long movieId) {
        List<Comment> comments = commentRepository.findByMovieMovieId(movieId);
        return commentMapper.commentsToCommentResponseDtos(comments);
    }

    // Delete a comment
    public boolean deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }

    // Calculate average rating for a movie
    public double calculateAverageRatingForMovie(long movieId) {
        List<Comment> comments = commentRepository.findByMovieMovieId(movieId);
        if (comments.isEmpty()) {
            return 0.0;
        }
        double totalScore = comments.stream().mapToDouble(Comment::getScore).sum();
        return totalScore / comments.size();
    }
}