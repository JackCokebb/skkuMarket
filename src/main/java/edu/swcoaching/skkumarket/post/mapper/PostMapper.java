package edu.swcoaching.skkumarket.post.mapper;

import edu.swcoaching.skkumarket.post.dto.PostResponseDto;
import edu.swcoaching.skkumarket.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    default PostResponseDto postToPostResponseDto(Post post){
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getStatus(),
                post.getMember().getId());
    }
}
