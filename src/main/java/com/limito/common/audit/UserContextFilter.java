package com.limito.common.audit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserContextFilter extends OncePerRequestFilter {

    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_USER_EMAIL = "X-User-Email";
    public static final String HEADER_USER_ROLE = "X-User-Role";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            // 1. 헤더에서 값 꺼내기
            String userIdHeader = request.getHeader(HEADER_USER_ID);
            String email = request.getHeader(HEADER_USER_EMAIL);
            String role = request.getHeader(HEADER_USER_ROLE);

            // 2. userId가 있으면 UserContext 세팅
            if (userIdHeader != null && !userIdHeader.isBlank()) {
                Long userId = null;
                try {
                    userId = Long.valueOf(userIdHeader);
                } catch (NumberFormatException e) {
                }

                UserContext userContext = UserContext.builder()
                        .userId(userId)
                        .email(email)
                        .role(role)
                        .build();
                UserContextHolder.set(userContext);
            }

            // 3. 다음 필터/컨트롤러로 진행
            filterChain.doFilter(request, response);
        } finally {
            // 4. 요청 완료 시 정리
            UserContextHolder.clear();
        }
    }
}
