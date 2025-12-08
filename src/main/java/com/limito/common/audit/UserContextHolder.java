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

	public static Optional<String> getCurrentUserEmail() {
		return Optional.ofNullable(CONTEXT.get()).map(UserContext::getEmail);
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
