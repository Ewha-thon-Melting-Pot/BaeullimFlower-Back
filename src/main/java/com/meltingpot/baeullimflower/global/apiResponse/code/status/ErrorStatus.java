package com.meltingpot.baeullimflower.global.apiResponse.code.status;

import com.meltingpot.baeullimflower.global.apiResponse.code.BaseErrorCode;
import com.meltingpot.baeullimflower.global.apiResponse.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "인증된 사용자 정보가 없습니다."),
    ALREADY_EXIST_STUDENTNUM(HttpStatus.BAD_REQUEST, "MEMBER4002", "이미 존재하는 학번 입니다."),
    NOT_EXIST_STUDENTNUM(HttpStatus.BAD_REQUEST, "MEMBER4003", "존재하지 않는 학번 입니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER4004", "비밀번호가 일치하지 않습니다."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "MEMBER4005", "유효하지 않은 사용자 입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "MEMBER4006", "유효하지 않은 리프레시 토큰 입니다."),

    // 게시물 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),
    EMAIL_REQUIRED(HttpStatus.FORBIDDEN,"EMAIL4003","이메일을 입력해주세요"),
    AGREE_REQUIRED(HttpStatus.FORBIDDEN, "AGREE4003","정보 동의해주세요"),
    PAGE_FORMAT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"PAGE4001","잘못된 페이지 번호 형식입니다"),

    // 투표 관련 에러

    // 결과 관련 에러
    ALREADY_EXIST_RESULT(HttpStatus.BAD_REQUEST, "RESULT4001", "해당 청원에 대한 논의 결과가 이미 존재합니다."),
    MUST_BE_DISCUSSION(HttpStatus.BAD_REQUEST, "RESULT4002", "청원글의 상태가 '학생회 논의중'일때만 결과를 작성할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}