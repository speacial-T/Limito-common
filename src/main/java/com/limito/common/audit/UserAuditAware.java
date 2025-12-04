package com.limito.common.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class UserAuditAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return UserContextHolder.getCurrentUserId();
	}

}
