package edu.swcoaching.skkumarket.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class MemberDto {
    @AllArgsConstructor
    @Getter
    public static class Post{
        @NotBlank
        private String username;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch{
        @Setter
        private long id;
        private String username;
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private long id;
        private String username;
        private String email;

    }
}
