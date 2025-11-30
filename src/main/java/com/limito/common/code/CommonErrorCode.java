package com.limito.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 서버 오류
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 입력값 검증
    INVALID_INPUT("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),

    // 인증/인가
    UNAUTHORIZED("인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("접근이 거부되었습니다.", HttpStatus.FORBIDDEN),

    // HTTP 요청
    METHOD_NOT_ALLOWED("지원하지 않는 API 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE("지원하지 않는 요청 형식입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    private final String message;
    private final HttpStatus status;

}
