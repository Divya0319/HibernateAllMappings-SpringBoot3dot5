package com.fastturtle.hibernateallmappingsspringboot.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST,"/booksReferred/*/bookReviews").authenticated()
                                .anyRequest().permitAll()
                ).formLogin(f -> f
                        .loginPage("/login")
                        .successHandler(customSuccessHandler())
                        .permitAll()
                ).logout(logout -> logout
                                .logoutUrl("/logout").permitAll()
                                .logoutSuccessUrl("/login?logout=true")
                );
//                .exceptionHandling(e -> e
//                        .authenticationEntryPoint((req, res, authEx) -> {
//                            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
//                                res.sendError(HttpServletResponse.SC_FORBIDDEN);
//                            } else {
//                                res.sendRedirect("/login");
//                            }
//                        })
//                );

        return http.build();

    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
                if (redirectUrl != null) {
                    session.removeAttribute("redirectAfterLogin");
                    response.sendRedirect(redirectUrl);
                    return;
                }
            }

            // fallback
            response.sendRedirect("/");
        };
    }
}
