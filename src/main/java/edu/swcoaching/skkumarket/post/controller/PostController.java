package edu.swcoaching.skkumarket.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.swcoaching.skkumarket.global.dto.SingleResponseDto;
import edu.swcoaching.skkumarket.post.dto.PostNewPostDto;
import edu.swcoaching.skkumarket.post.dto.PostResponseDto;
import edu.swcoaching.skkumarket.post.entity.Post;
import edu.swcoaching.skkumarket.post.mapper.PostMapper;
import edu.swcoaching.skkumarket.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final ObjectMapper objectMapper;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<SingleResponseDto<?>> postNewPost(@RequestBody PostNewPostDto requestBody){
        Post createdPost = postService.createPost(requestBody);
        PostResponseDto responseBody = postMapper.postToPostResponseDto(createdPost);
        log.debug("# responseBody: {}", responseBody.toString());
        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        true,
                        responseBody
                ),
                HttpStatus.CREATED
        );
    }
}
