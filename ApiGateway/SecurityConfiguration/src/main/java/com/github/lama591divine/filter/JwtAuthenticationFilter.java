package com.github.lama591divine.filter;

import com.github.lama591divine.utility.JwtUtility;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtility jwt;
    private final UserDetailsService userDetailsService;

    private static final Pattern BEARER_PATTERN = Pattern.compile("^(?i)Bearer\\s+(.+)$");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return "/login".equals(req.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
            return;
        }

        Matcher matcher = BEARER_PATTERN.matcher(authHeader);
        if (!matcher.matches()) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization schema");
            return;
        }

        String token = matcher.group(1);

        try {
            String login = jwt.parse(token).getSubject();
            UserDetails ud = userDetailsService.loadUserByUsername(login);

            if (jwt.validateToken(token, ud.getUsername())) {
                Authentication auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(req, res);
            } else {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            }

        } catch (JwtException | IllegalArgumentException ex) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token error: " + ex.getMessage());
        }
    }
}
