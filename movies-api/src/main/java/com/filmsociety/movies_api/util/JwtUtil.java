package com.filmsociety.movies_api.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public final class JwtUtil {
    private static final String SECRET_KEY = "MySecretKeyThatIsAtLeast32CharactersLong!!";

    private JwtUtil() {}

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isTokenValid(String token) {
        try {
            parseToken(token); // if this works, token is valid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateToken(Long userId, boolean isAdmin) {
        long expirationTime = 1000L * 60 * 60 * 24; // 24 hours

        return Jwts.builder()
                .claim("userId", userId)
                .claim("isAdmin", isAdmin)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}