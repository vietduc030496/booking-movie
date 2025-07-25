package com.ntu.site.infrastucture.config.security;

import com.ntu.site.infrastucture.config.filter.CaptchaValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ntu.common.constant.UrlConstant.*;


@Configuration
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/css/**",
            "/js/**",
            "/images/**",
            "/favicon.ico",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/",
            "/home/**",
            "/test/**",
            ACCOUNT_VIEW_URL + "/login/**",
            API_VERSION + "/**",
            ERROR_URL + "/**",
            MOVIE_VIEW_URL + "/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(AUTH_WHITELIST)
                                .permitAll()
                                .anyRequest()
//                                .authenticated()
                                .permitAll()
                )
                .formLogin(form -> form
                        .loginPage(ACCOUNT_VIEW_URL + "/login")
                        .loginProcessingUrl(ACCOUNT_VIEW_URL + "/login")
                        .successHandler((request, response, authentication) -> {
                            String redirectUrl = (String) request.getSession().getAttribute("REDIRECT_AFTER_LOGIN");
                            if (redirectUrl != null) {
                                request.getSession().removeAttribute("REDIRECT_AFTER_LOGIN");
                                response.sendRedirect(redirectUrl);
                            } else {
                                response.sendRedirect("/home"); // default nếu không có redirect
                            }
                        })
                        .failureUrl(ACCOUNT_VIEW_URL + "/login?error=true")
                )
                .addFilterBefore(new CaptchaValidationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                request.getRequestDispatcher(ERROR_URL + "/403").forward(request, response))
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
