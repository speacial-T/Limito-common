package com.limito.common.exception;

import com.limito.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException exception) {
        log.error("[AppException] status={} message={}",
                exception.getStatus(), exception.getMessage(), exception);

        // AppException은 status + message만 사용
        return ErrorResponse.errorResponse(exception.getStatus(), exception.getMessage());
    }

    // 인증/인가
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = "접근 권한이 없습니다.";
        log.error("[AccessDeniedException] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "인증이 필요합니다.";
        log.error("[AuthenticationException] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }

    // 입력값 검증
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            BindException.class
    })
    public ResponseEntity<ErrorResponse> handleValidation(Exception exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "요청 값이 유효하지 않습니다.";
        log.error("[ValidationException] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }


    // Http 요청
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupport(
            HttpRequestMethodNotSupportedException exception) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        String message = "허용되지 않는 HTTP 메서드입니다.";
        log.error("[HttpRequestMethodNotSupportedException] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParserError(
            HttpMessageNotReadableException exception) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "요청 본문을 읽을 수 없습니다.";
        log.error("[HttpMessageNotReadableException] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupport(
            HttpMediaTypeNotSupportedException exception) {

        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        String message = "지원하지 않는 Content-Type 입니다.";
        log.error("[HttpMediaTypeNotSupportedException] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }

    // 그 외 예외처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "서버 오류가 발생했습니다.";
        log.error("[Exception] {}", message, exception);
        return ErrorResponse.errorResponse(status, message);
    }
}
