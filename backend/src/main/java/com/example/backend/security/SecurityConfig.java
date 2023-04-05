package com.example.backend.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .securityContext().requireExplicitSave(false).and()
                                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("https://evil-evaluators.herokuapp.com"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and()
                .csrf().disable()
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").authenticated()
                .antMatchers(HttpMethod.GET, "/alert").hasAnyRole("ADMIN", "MEMBER")
                .antMatchers(HttpMethod.POST, "/alert").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/alert").hasRole("ADMIN")
                .antMatchers("/member**").hasRole("ADMIN")
                .antMatchers("/scan**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/records**").hasAnyRole("ADMIN", "MEMBER")
                .antMatchers(HttpMethod.DELETE, "/records**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/threats/count").hasAnyRole("ADMIN", "MEMBER")
                .antMatchers(HttpMethod.POST, "/records/file").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

