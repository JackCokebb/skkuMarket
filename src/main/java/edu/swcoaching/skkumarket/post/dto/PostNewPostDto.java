package edu.swcoaching.skkumarket.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostNewPostDto {
    private String title;
    private String image;
    private Integer price;
    private String description;
    private Long memberId;
}
