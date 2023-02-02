package edu.swcoaching.skkumarket.member.dto;

import edu.swcoaching.skkumarket.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class MemberDto { // inner class 별로 -> 별개로 만드는게 나을수도
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
    public static class Put{
        @Setter
        private long id;
        private String username;
        private String email;
        private String password;
        private Member.Status status;
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private long id;
        private String nickname;
        private String email;
        private Member.Status status;

    }
}
