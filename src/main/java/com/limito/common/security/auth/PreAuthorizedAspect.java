package com.limito.common.security.auth;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.limito.common.exception.AppException;
import com.limito.common.security.audit.UserRole;
import com.limito.common.security.context.UserContextHolder;

@Aspect
@Component
public class PreAuthorizedAspect {
	@Around("@within(com.limito.common.security.auth.PreAuthorized) || "
		+ "@annotation(com.limito.common.security.auth.PreAuthorized)")
	public Object checkAuthorization(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();

		// 메서드에서 없으면 클래스에서 찾기
		PreAuthorized preAuthorized = method.getAnnotation(PreAuthorized.class);
		if (preAuthorized == null) {
			preAuthorized = method.getDeclaringClass().getAnnotation(PreAuthorized.class);
		}

		// 어노테이션이 없으면 그냥 진행
		if (preAuthorized == null) {
			return proceedingJoinPoint.proceed();
		}

		UserRole currentRole = UserContextHolder.getCurrentUserRole()
			.orElseThrow(() -> AppException.of(HttpStatus.UNAUTHORIZED, "사용자 정보가 없습니다."));

		boolean allowed = Arrays.stream(preAuthorized.value())
			.anyMatch(r -> r.equals(currentRole));

		if (!allowed) {
			throw AppException.of(HttpStatus.FORBIDDEN, "권한이 없습니다.");
		}

		return proceedingJoinPoint.proceed();
	}
}
