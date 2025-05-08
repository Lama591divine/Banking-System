package com.github.Lama591divine.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtility {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateJwt(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractLogin(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String extractLogin(String token) {
        return parse(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return parse(token).getExpiration().before(new Date());
    }

    public Claims parse(String token) {
        return Jwts.parser().verifyWith((SecretKey)key).build().parseSignedClaims(token).getPayload();
    }
}
