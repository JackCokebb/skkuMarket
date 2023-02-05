package edu.swcoaching.skkumarket.post.dto;

import edu.swcoaching.skkumarket.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private Post.Status status;
    private Long memberId;

    @Override
    public String toString() {
        return "PostResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", memberId=" + memberId +
                '}';
    }
}
