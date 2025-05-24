package com.github.lama591divine.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lama591divine.utility.JwtUtility;
import com.github.lama591divine.dto.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtility jwtUtility;
    private final ObjectMapper objectMapper;

    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtUtility jwtUtility, ObjectMapper objectMapper) {
        this.jwtUtility = jwtUtility;
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

            return getAuthenticationManager().authenticate(authToken);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to read request body", e);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Unexpected authentication error", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        UserDetails userDetails = (UserDetails)authResult.getPrincipal();
        String token = jwtUtility.generateJwt(userDetails);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);

        objectMapper.writeValue(response.getWriter(), responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, String> error = Map.of(
                "error", "Неверные учетные данные",
                "details", failed.getMessage());

        objectMapper.writeValue(response.getWriter(), error);
    }
}
