package edu.swcoaching.skkumarket.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> {
    private boolean success;
    private T data;
}