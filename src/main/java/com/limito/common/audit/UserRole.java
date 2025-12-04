package com.limito.common.audit;

public enum UserRole {

	ADIMIN("관리자"),
	COMPANY("업체"),
	USER("일반 사용자");

	private final String role;

	UserRole(String role) {
		this.role = role;
	}
}