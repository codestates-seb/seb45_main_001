package com.sundayCinema.sundayCinema.comment;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.CommentPostDto commentPostDto);
    Comment commentPatchDtoToComment(CommentDto.CommentPatchDto commentPatchDto);
    CommentDto.CommentResponseDto commentToCommentResponseDto(Comment comment);
    List<CommentDto.CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments);
}
