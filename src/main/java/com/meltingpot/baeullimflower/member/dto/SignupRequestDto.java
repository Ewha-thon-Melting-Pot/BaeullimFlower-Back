package com.meltingpot.baeullimflower.member.dto;

import com.meltingpot.baeullimflower.member.domain.College;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {
    @NotBlank(message = "이름은 필수로 입력되어야 합니다.") //해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
    private String name;

    @NotBlank(message = "학번은 필수로 입력되어야 합니다.")
    private String studentNum;

    @NotBlank(message = "단과대학은 필수로 입력되어야 합니다.")
    private College college;

    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    private String password;
}
