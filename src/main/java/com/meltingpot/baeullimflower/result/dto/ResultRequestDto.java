package com.meltingpot.baeullimflower.result.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 디폴트 생성자를 자동으로 생성
public class ResultRequestDto {
    @NotNull(message = "내용은 필수로 입력되어야 합니다.") // 공백까지는 허용
    private String content;
}
