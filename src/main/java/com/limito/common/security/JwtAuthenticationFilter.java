package com.limito.common.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.limito.common.audit.UserContext;
import com.limito.common.audit.UserContextHolder;
import com.limito.common.audit.UserRole;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String HEADER_USER_ID = "X-User-ID";
	public static final String HEADER_USER_ROLE = "X-User-Role";

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		try {
			String userIdHeader = request.getHeader(HEADER_USER_ID);
			String roleHeader = request.getHeader(HEADER_USER_ROLE);

			if (userIdHeader != null && roleHeader != null) {
				Long userId = Long.valueOf(userIdHeader);
				UserRole role = UserRole.valueOf(roleHeader);

				// SecurityContext 설정
				CustomUserPrincipal principal = new CustomUserPrincipal(userId, role);
				UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

				// Auditing용 UserContext 설정
				UserContext userContext = UserContext.builder()
					.userId(userId)
					.role(role.name())
					.build();
				UserContextHolder.set(userContext);
			}
			filterChain.doFilter(request, response);
		} finally {
			UserContextHolder.clear();
		}
	}

}
