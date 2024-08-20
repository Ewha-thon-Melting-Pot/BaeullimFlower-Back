package com.meltingpot.baeullimflower.post.dto;

import com.meltingpot.baeullimflower.post.domain.Categrory;
import lombok.Getter;

import java.util.List;


public class PostRequestDto {
    @Getter
    public static class PostCreateDto {
        private String email;
        private Boolean infoAgree;
        private String title;
        private String content;
        private Categrory categrory;
        List<String> urlList;
    }
}
