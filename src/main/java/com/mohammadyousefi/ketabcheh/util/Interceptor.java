package com.mohammadyousefi.ketabcheh.util;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
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
        if (handler instanceof HandlerMethod handlerMethod) {
            if (handlerMethod.hasMethodAnnotation(Authorization.class)) {
                String token = request.getHeader("Authorization");
                if (token == null || !token.startsWith("Bearer ") || !jwt.isTokenValid(token.substring(7)))
                    System.out.println("");
                request.setAttribute("userId", jwt.extractId(token.substring(7)));
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
