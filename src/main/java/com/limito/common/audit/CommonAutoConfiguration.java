package com.limito.common.audit;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@AutoConfiguration
@ComponentScan("com.limito.common")
@EnableJpaAuditing
public class CommonAutoConfiguration {

	// @Bean
	// @ConditionalOnMissingBean
	// public FilterRegistrationBean<UserContextFilter> userContextFilterRegistration() {
	// 	FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean<>();
	// 	registrationBean.setFilter(new UserContextFilter());
	// 	registrationBean.addUrlPatterns("/api/*");
	// 	registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
	// 	return registrationBean;
	// }

	@Bean
	@ConditionalOnMissingBean(AuditorAware.class)
	public AuditorAware<Long> auditorAware() {
		return new UserAuditAware();
	}
}
