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
        public void setEmail(String email) {
            this.email = email;
        }
        public void setInfoAgree(Boolean infoAgree) {
            this.infoAgree = infoAgree;
        }
    }

    @Getter
    public static class PostPreDto {
        private String email;
        private Boolean infoAgree;
    }
}