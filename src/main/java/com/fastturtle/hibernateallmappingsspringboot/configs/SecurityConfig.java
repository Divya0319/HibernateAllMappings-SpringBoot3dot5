package com.fastturtle.hibernateallmappingsspringboot.configs;

import com.fastturtle.hibernateallmappingsspringboot.helper.CustomFailureHandler;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomFailureHandler customFailureHandler;

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
                        auth.requestMatchers(HttpMethod.POST,"/books/*/reviews").authenticated()
                                .anyRequest().permitAll()
                ).formLogin(f -> f
                        .loginPage("/login")
                        .failureHandler(customFailureHandler)
                        .successHandler(customSuccessHandler())
                        .permitAll()
                ).logout(logout -> logout
                                .logoutUrl("/logout").permitAll()
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    response.sendRedirect(request.getHeader("Referer"));
                                })
                );

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
