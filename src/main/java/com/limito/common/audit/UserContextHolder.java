package com.limito.common.audit;

import java.util.Optional;

public final class UserContextHolder {

	private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

	private UserContextHolder() {
	}

	public static void set(UserContext userContext) {
		CONTEXT.set(userContext);
	}

	public static UserContext get() {
		return CONTEXT.get();
	}

	public static Optional<UserContext> getOptional() {
		return Optional.ofNullable(CONTEXT.get());
	}

	public static Optional<Long> getCurrentUserId() {
		return Optional.ofNullable(CONTEXT.get()).map(UserContext::getUserId);
	}

	public static void clear() {
		CONTEXT.remove();
	}

}
