package com.mohammadyousefi.ketabcheh.util;

import com.mohammadyousefi.ketabcheh.auth.Admin;
import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.UnAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {
    private final Jwt jwt;

    public Interceptor(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod && (handlerMethod.hasMethodAnnotation(Authorization.class))) {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ") || Boolean.TRUE.equals(!jwt.isTokenValid(token.substring(7))))
                throw new UnAuthorizedException(ErrorMessages.unAuthorized());
            String correctToken = token.substring(7);
            if (((HandlerMethod) handler).hasMethodAnnotation(Admin.class) && !Boolean.TRUE.equals(jwt.isAdmin(correctToken)))
                throw new UnAuthorizedException(ErrorMessages.unAuthorized());
            request.setAttribute("userId", jwt.extractId(correctToken));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
