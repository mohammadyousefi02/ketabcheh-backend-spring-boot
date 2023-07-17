package com.mohammadyousefi.ketabcheh.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class Jwt {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String createToken(Map<String, Objects> claims, Long userId) {
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
}
