package com.mohammadyousefi.ketabcheh.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class Jwt {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Long userId) {
        Map<String, Objects> claims = new HashMap<>();
        return createToken(claims, userId);
    }

    public Boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private String createToken(Map<String, Objects> claims, Long userId) {
        return Jwts.builder()
                .signWith(generateKey())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(String.valueOf(userId))
                .compact();
    }

    private Key generateKey() {
        if (secretKey != null) return secretKey;
        secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
        return secretKey;
    }

    private Boolean isTokenExpired(String token) {
        Date date = extractExpiration(token);
        return date.before(new Date());
    }

    private Long extractId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getSubject));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody();
    }
}
