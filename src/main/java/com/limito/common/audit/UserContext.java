package com.limito.common.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

	private Long userId;
	private String role;

	public boolean hasRole(String role) {
		return this.role != null && this.role.equals(role);
	}

	public boolean isLoggedIn() {
		return this.userId != null;
	}
}
