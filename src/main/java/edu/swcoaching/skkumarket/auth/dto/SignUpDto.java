package edu.swcoaching.skkumarket.auth.dto;

import lombok.Getter;

import java.util.Objects;


public final class SignUpDto { // record -> class
    private final String email;
    private final String password;
    private final String nickname;

    public SignUpDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String nickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SignUpDto) obj;
        return Objects.equals(this.email, that.email) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, nickname);
    }

    @Override
    public String toString() {
        return "SignUpDto[" +
                "email=" + email + ", " +
                "password=" + password + ", " +
                "nickname=" + nickname + ']';
    }

}
