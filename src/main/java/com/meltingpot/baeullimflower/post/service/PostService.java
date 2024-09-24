package com.meltingpot.baeullimflower.post.service;

import com.meltingpot.baeullimflower.global.apiResponse.code.status.ErrorStatus;
import com.meltingpot.baeullimflower.global.apiResponse.exception.GeneralException;
import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import com.meltingpot.baeullimflower.member.service.MemberService;
import com.meltingpot.baeullimflower.post.Repository.PostRepository;
import com.meltingpot.baeullimflower.post.converter.PostConverter;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.dto.PostRequestDto;
import com.meltingpot.baeullimflower.post.dto.PostResponseDto;
import com.meltingpot.baeullimflower.vote.domain.Vote;
import com.meltingpot.baeullimflower.vote.dto.VoteResponseDto;
import com.meltingpot.baeullimflower.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 게시물 생성
    @Transactional
    public PostResponseDto.PostDto createPost(PostRequestDto.PostCreateDto request) {
        String memberId = memberService.getCurrentMemberId().toString();
        Member member = memberRepository.findByStudentNum(memberId);

        Post post = postConverter.toPostEntity(request, member);
        postRepository.save(post);
        PostResponseDto.PostDto postDto = postConverter.toPostDto(post);

        return postDto;
    }

    // 청원자 이메일, 정보 동의 확인
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

    // 게시물 상세 조회
    public PostResponseDto.PostDto getPostDetail(Long postId){
        Post post = findById(postId);
        PostResponseDto.PostDto postDto = postConverter.toPostDto(post);
        return postDto;
    }

    // 게시물 Id로 게시물 찾기
    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(()->new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }

    @Transactional
    // 게시물 투표
    public VoteResponseDto.VoteCreateDto enableVote(Long postId) {
        String memberId = memberService.getCurrentMemberId().toString();
        Member member = memberRepository.findByStudentNum(memberId);

        Post post = findById(postId);

        if(voteRepository.findByMemberAndPost(member, post) != null) {
            throw new GeneralException(ErrorStatus.ALREADY_VOTED);
        }

        post.setVoteCount(post.getVoteCount()+1);

        Vote vote = Vote.builder()
                .member(member)
                .post(post)
                .build();
        voteRepository.save(vote);

        VoteResponseDto.VoteCreateDto voteCreateDto = new VoteResponseDto.VoteCreateDto(vote.getVoteId(), vote.getPost().getPostId(), vote.getMember().getMemberId(),  vote.getPost().getVoteCount());
        return voteCreateDto;
    }

    @Transactional
    // 게시물 투표 취소
    public VoteResponseDto.VoteDeleteDto disableVote(Long postId) {
        String memberId = memberService.getCurrentMemberId().toString();
        Member member = memberRepository.findByStudentNum(memberId);

        Post post = findById(postId);

        if(voteRepository.findByMemberAndPost(member, post) == null) {
            throw new GeneralException(ErrorStatus.NEVER_VOTED);
        }

        post.setVoteCount(post.getVoteCount()-1);
        voteRepository.deleteByMemberAndPost(member, post);

        VoteResponseDto.VoteDeleteDto voteDeleteDto = new VoteResponseDto.VoteDeleteDto(post.getPostId(), post.getVoteCount());
        return voteDeleteDto;
    }

}
