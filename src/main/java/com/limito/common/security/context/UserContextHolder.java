package com.limito.common.security.context;

import java.util.Optional;

import com.limito.common.security.audit.UserRole;

public final class UserContextHolder {

	private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

	public static void set(UserContext userContext) {
		CONTEXT.set(userContext);
	}

	public static UserContext get() {
		return CONTEXT.get();
	}

	public static Optional<UserContext> getCurrentUser() {
		return Optional.ofNullable(CONTEXT.get());
	}

	public static Optional<Long> getCurrentUserId() {
		return Optional.ofNullable(CONTEXT.get()).map(UserContext::getUserId);
	}

	public static Optional<UserRole> getCurrentUserRole() {
		return Optional.ofNullable(CONTEXT.get())
			.map(UserContext::getRole)
			.map(UserRole::valueOf);
	}

	public static void clear() {
		CONTEXT.remove();
	}

}
