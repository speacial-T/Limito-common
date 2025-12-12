package com.limito.common.security.auth;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.limito.common.exception.AppException;
import com.limito.common.security.context.UserContextHolder;

public class CurrentUserArgResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class) && parameter.getParameterType().equals(
			UserContextHolder.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer modelAndViewContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		return UserContextHolder.getCurrentUser()
			.orElseThrow(() -> AppException.of(HttpStatus.UNAUTHORIZED, "사용자 정보가 없습니다."));
	}
}
