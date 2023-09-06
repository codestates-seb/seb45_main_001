package com.sundayCinema.sundayCinema.comment;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.CommentPostDto commentPostDto);
    Comment commentPatchDtoToComment(CommentDto.CommentPatchDto commentPatchDto);

    // 반환 타입을 CommentResponseDto로 수정
    CommentDto.CommentResponseDto commentToCommentResponseDto(Comment comment);

    List<CommentDto.CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments);
}
