package com.limito.common.security.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.limito.common.security.context.UserContextHolder;

@Component
public class UserAuditAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {

		// UserContext 에서 userId 꺼내보고, 없으면 0L(SYSTEM)로 사용
		Long auditorId = UserContextHolder.getCurrentUserId()
			.orElse(0L);

		return Optional.of(auditorId);
	}

}
