package com.limito.common.security;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.limito.common.security.audit.UserAuditAware;
import com.limito.common.security.auth.CurrentUserArgResolver;
import com.limito.common.security.auth.PreAuthorizedAspect;
import com.limito.common.security.context.UserContextFilter;

@AutoConfiguration
@ComponentScan("com.limito.common")
@EnableJpaAuditing
public class CommonAutoConfiguration implements WebMvcConfigurer {

	// userContextFilter 등록
	@Bean
	@ConditionalOnMissingBean
	public FilterRegistrationBean<UserContextFilter> userContextFilterRegistration() {
		FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new UserContextFilter());
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
		return registrationBean;
	}

	// JPA Auditing용 AuditorAware
	@Bean
	@ConditionalOnMissingBean(AuditorAware.class)
	public AuditorAware<Long> auditorAware() {
		return new UserAuditAware();
	}

	// @CurrentUser 읽는 ArgumentResolver 추가
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new CurrentUserArgResolver());
	}

	// @PreAuthorized 처리하는 AOP Aspect 등록
	@Bean
	public PreAuthorizedAspect preAuthorizedAspect() {
		return new PreAuthorizedAspect();
	}

}
