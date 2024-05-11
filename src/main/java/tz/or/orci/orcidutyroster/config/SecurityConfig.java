package tz.or.orci.orcidutyroster.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tz.or.orci.orcidutyroster.model.entities.Token;
import tz.or.orci.orcidutyroster.repository.TokenRepository;
import tz.or.orci.orcidutyroster.security.JwtAuthenticationFilter;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenRepository tokenRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Value("${spring.ldap.urls[0]}")
    private String ldapUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(
                requests -> {
                    requests
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/actuator/**",
                                    "/api/v1/auth/login",
                                    "/api/v1/auth/self-register"
                            )
                            .permitAll();
                    requests
                            .requestMatchers("/api/v1/auth/register-by-admin")
                            .hasRole("ADMIN");
                    requests
                            .anyRequest()
                            .authenticated();
                });
        http.exceptionHandling(exception -> {
            exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                /* Don't have permissions to access resources */
                response.setStatus(FORBIDDEN.value());
            });
            exception.authenticationEntryPoint((request, response, authException) -> {
                /* No Token or invalid Token */
                response.setStatus(UNAUTHORIZED.value());
            });
        });
        http.logout(logout -> {
            logout.logoutUrl("/api/v1/auth/logout");
            logout.addLogoutHandler((request, response, authentication) -> {
                String authHeader = request.getHeader("Authorization");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return;
                }
                String token = authHeader.substring(7);
                Optional<Token> storedTokenOptional = tokenRepository.findByToken(token);

                if (storedTokenOptional.isPresent()) {
                    Token storedToken = storedTokenOptional.get();
                    storedToken.setRevoked(true);
                    tokenRepository.save(storedToken);
                }
            });
            logout.logoutSuccessHandler((request, response, authentication) -> response.setStatus(OK.value()));
        });

        return http.build();
    }

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider authenticationProvider() {
        return new ActiveDirectoryLdapAuthenticationProvider("net.orci", ldapUrl);
    }
}
