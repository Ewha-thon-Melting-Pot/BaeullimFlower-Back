package com.meltingpot.baeullimflower.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String studentNum;
    private String password;
}
