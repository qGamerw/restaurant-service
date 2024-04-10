package ru.sber.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Настройка конфигурации для защиты конечных точек аутентификации и авторизации
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO: Сделать перехват исключений при просроченном токене
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize ->
                        authorize.requestMatchers(
                                        "/api/auth/signin",
                                        "/api/auth/signup",
                                        "/api/auth/refresh",
                                        "/api/auth/reset-password/token",
                                        "/api/auth/reset-password",
                                        "/dishes/customer",
                                        "/dishes/customer/**",
                                        "/categories")
                                .permitAll()
                                .anyRequest()
                                .authenticated()))
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .build();
    }
}