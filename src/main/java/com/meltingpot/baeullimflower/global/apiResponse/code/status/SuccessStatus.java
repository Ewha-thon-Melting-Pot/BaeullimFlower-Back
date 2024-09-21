package com.meltingpot.baeullimflower.global.apiResponse.code.status;

import com.meltingpot.baeullimflower.global.apiResponse.code.BaseCode;
import com.meltingpot.baeullimflower.global.apiResponse.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    CREATE_SUCCESS(HttpStatus.CREATED, "COMMON201", "생성에 성공했습니다."),
    DELETE_SUCCESS(HttpStatus.OK, "COMMON202", "삭제에 성공했습니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
