package com.ntu.site.infrastucture.config.filter;

import com.ntu.common.util.CaptchaUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ntu.common.constant.UrlConstant.ACCOUNT_VIEW_URL;


@Component
public class CaptchaValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String url = ACCOUNT_VIEW_URL + "/login";

        if (url.equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String captchaSession = (String) request.getSession().getAttribute(CaptchaUtil.CAPTCHA_KEY);
            String captchaUserInput = request.getParameter("captcha");
            String username = request.getParameter("username");
            if (captchaSession == null || !captchaSession.equalsIgnoreCase(captchaUserInput)) {
                response.sendRedirect(url + "?captchaError=true&username=" + username);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
