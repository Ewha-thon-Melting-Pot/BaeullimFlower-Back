package com.meltingpot.baeullimflower.post.service;

import com.meltingpot.baeullimflower.global.apiResponse.code.status.ErrorStatus;
import com.meltingpot.baeullimflower.global.apiResponse.exception.GeneralException;
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
    public PostResponseDto.PostDto createPost(PostRequestDto.PostCreateDto request) {
        Post post = postConverter.toPostEntity(request);
        postRepository.save(post);
        PostResponseDto.PostDto postDto = postConverter.toPostDto(post);

        return postDto;
    }

    public PostResponseDto.PostPreDto prePost(PostRequestDto.PostPreDto request) {
        String email = request.getEmail();
        Boolean infoAgree = request.getInfoAgree();

        if (email == null || email.isEmpty()) {
            throw new GeneralException(ErrorStatus.EMAIL_REQUIRED);
        }
        if (infoAgree == null || !infoAgree) {
            throw new GeneralException(ErrorStatus.AGREE_REQUIRED);
        }

        PostResponseDto.PostPreDto postPreDto = new PostResponseDto.PostPreDto(email, infoAgree);
        return postPreDto;
    }

    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(()->new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }

    public PostResponseDto.PostDto getPostDetail(Long postId){
        Post post = findById(postId);
        PostResponseDto.PostDto postDto = postConverter.toPostDto(post);
        return postDto;
    }
}
