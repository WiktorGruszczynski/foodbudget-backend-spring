package org.example.foodbudgetbackendspring.config;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${server.servlet.session.cookie.max-age:7d}")
    private Duration sessionMaxAge;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        JsonAuthenticationFilter jsonFilter = new JsonAuthenticationFilter(
                authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))
        );

        jsonFilter.setSecurityContextRepository(
                new HttpSessionSecurityContextRepository()
        );

        jsonFilter.setAuthenticationSuccessHandler((
                request, response, authentication) -> {
                    Cookie authCookie = new Cookie("AUTHENTICATED", "true");
                    authCookie.setDomain("wiktor-gruszczynski.pl");
                    authCookie.setHttpOnly(false);
                    authCookie.setPath("/");
                    authCookie.setMaxAge((int) sessionMaxAge.getSeconds());
                    response.addCookie(authCookie);

                    response.setStatus(200);
                }
        );
        jsonFilter.setAuthenticationFailureHandler(
                (request, response, authentication) -> response.setStatus(401)
        );

        http
                .addFilter(jsonFilter)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/ping").permitAll()
                        .requestMatchers("/auth/validate-session").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("SESSION")
                        .addLogoutHandler((request, response, authentication) -> {

                            Cookie authCookie = new Cookie("AUTHENTICATED", null);
                            authCookie.setPath("/");
                            authCookie.setMaxAge(0);
                            response.addCookie(authCookie);
                        })
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "https://food.wiktor-gruszczynski.pl"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
