package com.limito.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

	private final int status;
	private final String message;

	// HttpStatus + message 기반
	public static ResponseEntity<ErrorResponse> errorResponse(HttpStatus status, String message) {
		return ResponseEntity
			.status(status)
			.body(new ErrorResponse(status.value(), message));
	}

}
