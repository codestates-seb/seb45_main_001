package com.sundayCinema.sundayCinema.comment;
import com.sundayCinema.sundayCinema.exception.BusinessLogicException;
import com.sundayCinema.sundayCinema.exception.ExceptionCode;
import com.sundayCinema.sundayCinema.logIn.utils.UserAuthService;
import com.sundayCinema.sundayCinema.member.Member;
import com.sundayCinema.sundayCinema.member.MemberRepository;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserAuthService userAuthService;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          UserAuthService userAuthService, MemberRepository memberRepository, MovieRepository movieRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userAuthService = userAuthService;
        this.memberRepository = memberRepository;
        this.movieRepository = movieRepository;
    }

    // Create a new comment
    public CommentDto.CommentResponseDto createComment(CommentDto.CommentPostDto commentPostDto,
                                                       long memberId, long movieId, HttpServletRequest request) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (member == null) {
            // 사용자 또는 영화가 존재하지 않는 경우 처리
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        if (movie == null){
            throw new BusinessLogicException(ExceptionCode.MOVIE_NOT_FOUND);
        }
        if(userAuthService.getSignedInUserEmail(request).equals(member.getEmail())) {
            double score = commentPostDto.getScore();
            String content = commentPostDto.getContent();
            if (score < 0 || score > 5 || content.length() > 100) {
                throw new InvalidInputException("평점(score)은 0에서 5 사이의 값이어야 하며, 댓글 내용(content)은 최대 100자여야 합니다.");
            }
            if (commentRepository.existsByMemberAndMovie(member, movie)) {
                throw new DuplicateCommentException("이미 댓글과 평점을 작성했습니다.");
            }
            Comment comment = commentMapper.commentPostDtoToComment(commentPostDto);
            comment.setMember(member);
            comment.setMovie(movie);
            comment = commentRepository.save(comment);
            CommentDto.CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(comment);
            return commentResponseDto;
        }else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }
    }

    // Update an existing comment
    public CommentDto.CommentResponseDto updateComment(CommentDto.CommentPatchDto commentPatchDto,long memberId,
                                                       long movieId, HttpServletRequest request) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (member == null) {
            // 사용자 또는 영화가 존재하지 않는 경우 처리
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        if (movie == null){
            throw new BusinessLogicException(ExceptionCode.MOVIE_NOT_FOUND);
        }

        if(userAuthService.getSignedInUserEmail(request).equals(member.getEmail())) {

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
        }else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }

    }

    // Get comments for a movie
    public List<CommentDto.CommentResponseDto> getCommentsForMovie(long movieId, long memberId, HttpServletRequest request) {
        // 사용자와 영화 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MOVIE_NOT_FOUND));

        // 현재 로그인한 사용자의 이메일 가져오기
        String signedInUserEmail = userAuthService.getSignedInUserEmail(request);

        // 현재 로그인한 사용자의 이메일과 회원의 이메일 비교
        if (!signedInUserEmail.equals(member.getEmail())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }

        // 영화에 대한 댓글 가져오기
        List<Comment> comments = commentRepository.findByMovieMovieId(movieId);

        // Comment 객체를 CommentResponseDto로 변환하여 반환
        return commentMapper.commentsToCommentResponseDtos(comments);
    }


    // Delete a comment
    public boolean deleteComment(long commentId, long memberId, long movieId, HttpServletRequest request) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (member == null) {
            // 사용자 또는 영화가 존재하지 않는 경우 처리
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        if (movie == null){
            throw new BusinessLogicException(ExceptionCode.MOVIE_NOT_FOUND);
        }


        if(userAuthService.getSignedInUserEmail(request).equals(member.getEmail())) {

        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
        }else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }
    }

    // Calculate average rating for a movie
    public double calculateAverageRatingForMovie(Long movieId, Long memberId, HttpServletRequest request) {
        // memberId가 null이면 Bad Request로 처리


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MOVIE_NOT_FOUND));

        // 현재 로그인한 사용자의 이메일 가져오기
        String signedInUserEmail = userAuthService.getSignedInUserEmail(request);

        // 현재 로그인한 사용자의 이메일과 회원의 이메일 비교
        if (!signedInUserEmail.equals(member.getEmail())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_SIGNED_IN);
        }
        List<Comment> comments = commentRepository.findByMovieMovieId(movieId);
        if (comments.isEmpty()) {
            return 0.0;
        }
        double totalScore = comments.stream().mapToDouble(Comment::getScore).sum();
        return totalScore / comments.size();
    }

}