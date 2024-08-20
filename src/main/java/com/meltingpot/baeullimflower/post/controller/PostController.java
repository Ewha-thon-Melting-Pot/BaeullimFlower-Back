package com.meltingpot.baeullimflower.post.controller;

import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.dto.PostRequestDto;
import com.meltingpot.baeullimflower.post.dto.PostResponseDto;
import com.meltingpot.baeullimflower.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public Post createPost(@RequestBody @Valid PostRequestDto.PostCreateDto request) {
        return postService.createPost(request);
    }

    @GetMapping("")
    public PostResponseDto.PostDto getPostDetail(@RequestParam Long postId) {
        return postService.getPostDetail(postId);
    }

}
