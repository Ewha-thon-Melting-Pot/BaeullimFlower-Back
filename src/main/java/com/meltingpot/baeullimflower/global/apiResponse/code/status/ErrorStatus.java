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
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4003", "이미 존재하는 이메일 입니다."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "MEMBER4004", "유효하지 않은 사용자 입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "MEMBER4005", "유효하지 않은 리프레시 토큰 입니다."),

    // 게시물 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),
    EMAIL_REQUIRED(HttpStatus.FORBIDDEN,"EMAIL4003","이메일을 입력해주세요"),
    AGREE_REQUIRED(HttpStatus.FORBIDDEN, "AGREE4003","정보 동의해주세요"),
    PAGE_FORMAT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"PAGE4001","잘못된 페이지 번호 형식입니다"),

    // 투표 관련 에러
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "VOTE4001","이미 투표한 게시물입니다"),
    NEVER_VOTED(HttpStatus.BAD_REQUEST,"VOTE4002","투표한 적 없는 게시물입니다");

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