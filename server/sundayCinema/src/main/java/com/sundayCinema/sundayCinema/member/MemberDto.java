package com.sundayCinema.sundayCinema.member;

import com.google.api.services.youtube.model.PageInfo;
import lombok.*;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MemberPostDto{
        @NotBlank
        @Email
        private String email;

        private String userName;

        private String password;

    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class MemberPatchDto{

        private String userName;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class MemberResponseDto {
        private long memberId;

        private String email;

        private String userName;

        private String password;

    }
    @AllArgsConstructor
    @Getter
    public static class SingleResponseDto<T>{
        private T data;
    }

    @Getter
    public static class MultiResponseDto<T> {
        private List<T> data;
        private PageInfo pageInfo;

        public MultiResponseDto(List<T> data, Page page) {
            this.data = data;
            this.pageInfo = new PageInfo(
            );
        }
    }
}
