package edu.swcoaching.skkumarket.auth.dto;

import lombok.Builder;

@Builder
public class AuthDto {
    private Long userId;
    private String accessToken;
}
