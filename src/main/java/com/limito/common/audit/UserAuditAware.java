package com.limito.common.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class UserAuditAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return UserContextHolder.getCurrentUserId();
    }

}
