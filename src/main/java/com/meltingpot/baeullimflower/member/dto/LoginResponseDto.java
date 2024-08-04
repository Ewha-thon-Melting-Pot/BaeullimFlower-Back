package com.meltingpot.baeullimflower.member.dto;

import com.meltingpot.baeullimflower.member.domain.College;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponseDto(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
