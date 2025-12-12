package com.limito.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private int status;
	private String message;

	// HttpStatus + message 기반
	public static ResponseEntity<ErrorResponse> errorResponse(HttpStatus status, String message) {
		return ResponseEntity
			.status(status)
			.body(new ErrorResponse(status.value(), message));
	}

}
