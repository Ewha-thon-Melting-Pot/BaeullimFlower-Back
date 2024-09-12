package com.meltingpot.baeullimflower.member.dto;

import com.meltingpot.baeullimflower.member.domain.College;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String name;
    private String studentNum;
    private College college;
    private String password;
}
