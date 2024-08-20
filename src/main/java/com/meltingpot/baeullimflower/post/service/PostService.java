package com.meltingpot.baeullimflower.post.service;

import com.meltingpot.baeullimflower.post.Repository.PostRepository;
import com.meltingpot.baeullimflower.post.converter.PostConverter;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.dto.PostRequestDto;
import com.meltingpot.baeullimflower.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    @Transactional
    public Post createPost(PostRequestDto.PostCreateDto request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
        //GeneralException -> EMAIL_REQUIRED
        }

        // 동의하지 않은 경우 예외 처리
        if (request.getInfoAgree() == null || !request.getInfoAgree()) {
        //GeneralException -> INFOAGREE_REQUIRED
        }

        Post post = postConverter.toPostEntity(request);
        postRepository.save(post);

        return post;
    }

    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(()->new RuntimeException());
        // 해당 게시물 없을 경우 에러처리 -> GeneralException(POST_NOT_FOUND)
    }

    public PostResponseDto.PostDto getPostDetail(Long postId){
        Post post = findById(postId);
        PostResponseDto.PostDto postDto = postConverter.toPostDto(post);
        return postDto;
    }
}
