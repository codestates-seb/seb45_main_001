package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.comment.CommentDto.CommentPatchDto;
import com.sundayCinema.sundayCinema.comment.CommentDto.CommentPostDto;
import com.sundayCinema.sundayCinema.comment.CommentDto.CommentResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-13T06:47:05+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(CommentPostDto commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( commentPostDto.getContent() );
        comment.setScore( commentPostDto.getScore() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto) {
        if ( commentPatchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( commentPatchDto.getCommentId() );
        comment.setContent( commentPatchDto.getContent() );
        comment.setScore( commentPatchDto.getScore() );

        return comment;
    }

    @Override
    public CommentResponseDto commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto();

        commentResponseDto.setContent( comment.getContent() );
        if ( comment.getCommentId() != null ) {
            commentResponseDto.setCommentId( comment.getCommentId() );
        }
        commentResponseDto.setScore( comment.getScore() );

        return commentResponseDto;
    }

    @Override
    public List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDto> list = new ArrayList<CommentResponseDto>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentResponseDto( comment ) );
        }

        return list;
    }
}
