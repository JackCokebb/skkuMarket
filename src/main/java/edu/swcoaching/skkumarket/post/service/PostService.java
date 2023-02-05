package edu.swcoaching.skkumarket.post.service;

import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.service.MemberService;
import edu.swcoaching.skkumarket.post.dto.PostNewPostDto;
import edu.swcoaching.skkumarket.post.entity.Post;
import edu.swcoaching.skkumarket.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    public Post createPost(PostNewPostDto postInfo){
        Member targetMember = memberService.findMemberById(postInfo.getMemberId());
        Post post = new Post(
                postInfo.getTitle(),
                postInfo.getImage(),
                postInfo.getPrice(),
                postInfo.getDescription(),
                Post.Status.ACTIVE
        );
        post.addMember(targetMember);
        return postRepository.save(post);
    }
}
