package com.limito.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

	private final HttpStatus status;

	public AppException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	private static AppException of(HttpStatus status, String message) {
		return new AppException(status, message);
	}

}
