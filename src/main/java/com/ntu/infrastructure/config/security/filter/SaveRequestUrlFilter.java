package com.ntu.infrastructure.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SaveRequestUrlFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
//        boolean isLoginPage = uri.startsWith("/login") || uri.startsWith("/css") || uri.startsWith("/js");

        if (request.getUserPrincipal() == null) {
            session.setAttribute("REDIRECT_AFTER_LOGIN", uri);
        }

        filterChain.doFilter(request, response);
    }
}
