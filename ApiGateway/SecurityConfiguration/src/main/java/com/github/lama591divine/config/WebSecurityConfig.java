package com.github.lama591divine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lama591divine.filter.JwtLoginFilter;
import com.github.lama591divine.filter.JwtAuthenticationFilter;
import com.github.lama591divine.utility.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public PasswordEncoder pwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(pwd());
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(DaoAuthenticationProvider p) {
        return new ProviderManager(p);
    }

    @Bean
    public JwtLoginFilter jwtLoginFilter(AuthenticationManager am,
                                         JwtUtility jwt,
                                         ObjectMapper om) {
        JwtLoginFilter filter = new JwtLoginFilter(am, jwt, om);
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthFilter(JwtUtility jwt, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwt, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtLoginFilter loginFilter,
                                           JwtAuthenticationFilter jwtFilter,
                                           DaoAuthenticationProvider provider) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(provider)

                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/client/**").hasRole("CLIENT")
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
