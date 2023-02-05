package edu.swcoaching.skkumarket.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthDto {
    private Long userId;
    private String accessToken;
}
