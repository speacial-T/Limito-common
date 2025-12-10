package com.limito.common.exception;

import java.nio.file.AccessDeniedException;

import org.apache.tomcat.websocket.AuthenticationException;
import org.hibernate.exception.ConstraintViolationException;
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

import com.limito.common.code.CommonErrorCode;
import com.limito.common.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

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
		CommonErrorCode code = CommonErrorCode.FORBIDDEN;
		log.error("[AccessDeniedException] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException exception) {
		CommonErrorCode code = CommonErrorCode.UNAUTHORIZED;
		log.error("[AuthenticationException] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
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
		CommonErrorCode code = CommonErrorCode.BAD_REQUEST;
		log.error("[ValidationException] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
	}

	// Http 요청
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotSupport(
		HttpRequestMethodNotSupportedException exception) {
		CommonErrorCode code = CommonErrorCode.METHOD_NOT_ALLOWED;
		log.error("[HttpRequestMethodNotSupportedException] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleJsonParserError(
		HttpMessageNotReadableException exception) {
		CommonErrorCode code = CommonErrorCode.BAD_REQUEST;
		log.error("[HttpMessageNotReadableException] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMediaTypeNotSupport(
		HttpMediaTypeNotSupportedException exception) {
		CommonErrorCode code = CommonErrorCode.UNSUPPORTED_MEDIA_TYPE;
		log.error("[HttpMediaTypeNotSupportedException] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
	}

	// 그 외 예외처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		CommonErrorCode code = CommonErrorCode.INTERNAL_SERVER_ERROR;
		log.error("[Exception] {}", code.getMessage(), exception);
		return ErrorResponse.errorResponse(code.getStatus(), code.getMessage());
	}
}
