package com.meltingpot.baeullimflower.post.converter;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.dto.PostRequestDto;
import com.meltingpot.baeullimflower.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostConverter {
    public Post toPostEntity(PostRequestDto.PostCreateDto request, Member member) {

        Post post = Post.builder()
                .writer(member)
                .email(request.getEmail())
                .infoAgree(request.getInfoAgree())
                .title(request.getTitle())
                .content(request.getContent())
                .categrory(request.getCategrory())
                .build();

        post.updateUrlList(request.getUrlList());
        return post;
    }


    public PostResponseDto.PostDto toPostDto(Post post) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdDate = post.getCreatedDate();
        LocalDateTime deadline = createdDate.plusDays(10);
        Duration dDay = Duration.between(deadline, now);

        PostResponseDto.PostDto postDto = PostResponseDto.PostDto.builder()
                .postId(post.getPostId())
                .status(post.getStatus())
                .writer(post.getWriter())
                .title(post.getTitle())
                .content(post.getContent())
                .categrory(post.getCategrory())
                .voteCount(post.getVoteCount())
                .createdDate(createdDate)
                .deadline(deadline)
                .dDay(dDay)
                .urlList(post.fetchUrlList())
                .build();

        return postDto;
    }
}