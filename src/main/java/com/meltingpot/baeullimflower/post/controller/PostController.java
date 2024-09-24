package com.meltingpot.baeullimflower.post.controller;

import com.meltingpot.baeullimflower.global.apiResponse.ApiResponse;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.dto.PostRequestDto;
import com.meltingpot.baeullimflower.post.dto.PostResponseDto;
import com.meltingpot.baeullimflower.post.service.PostService;
import com.meltingpot.baeullimflower.vote.dto.VoteResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/pre")
    public ApiResponse<PostResponseDto.PostPreDto> prePost(@RequestBody @Valid PostRequestDto.PostPreDto request, HttpSession session) {
        session.setAttribute("email", request.getEmail());
        session.setAttribute("infoAgree", request.getInfoAgree());
        return ApiResponse.onSuccess(postService.prePost(request));
    }

    @PostMapping("")
    public ApiResponse<PostResponseDto.PostDto> createPost(@RequestBody @Valid PostRequestDto.PostCreateDto request, HttpSession session) {
        String email = (String)session.getAttribute("email");
        Boolean infoAgree = (Boolean)session.getAttribute("infoAgree");
        request.setEmail(email);
        request.setInfoAgree(infoAgree);
        return ApiResponse.onCreateSuccess(postService.createPost(request));
    }

    @GetMapping("")
    public ApiResponse<PostResponseDto.PostDto> getPostDetail(@RequestParam Long postId) {
        return ApiResponse.onSuccess(postService.getPostDetail(postId));
    }

    @PutMapping("/{postId}/vote/enable")
    public ApiResponse<VoteResponseDto.VoteCreateDto> enableVote(@PathVariable Long postId) {
        return ApiResponse.onCreateSuccess(postService.enableVote(postId));
    }

    @PutMapping("/{postId}/vote/disable")
    public ApiResponse<VoteResponseDto.VoteDeleteDto> disableVote(@PathVariable Long postId) {
        return ApiResponse.onDeleteSuccess(postService.disableVote(postId));
    }

}