package com.limito.common.audit;

import java.util.Optional;

public final class UserContextHolder {

	private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

	public static void set(UserContext userContext) {
		CONTEXT.set(userContext);
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
