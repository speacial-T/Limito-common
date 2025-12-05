package com.limito.common.exception;

import org.springframework.http.HttpStatus;

import com.limito.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

	private final HttpStatus status;

	public AppException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
	}

	public AppException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public static AppException of(ErrorCode errorCode) {
		return new AppException(errorCode);
	}

	public static AppException of(HttpStatus status, String message) {
		return new AppException(status, message);
	}

}
