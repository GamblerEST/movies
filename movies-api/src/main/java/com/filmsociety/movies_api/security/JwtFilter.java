package com.filmsociety.movies_api.security;

import java.io.IOException;

import com.filmsociety.movies_api.exception.JwtAuthenticationException;
import com.filmsociety.movies_api.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                Claims claims = JwtUtil.parseToken(token);

                Long userId = claims.get("userId", Long.class);
                Boolean isAdmin = claims.get("isAdmin", Boolean.class);

                JwtAuthentication auth = new JwtAuthentication(userId, isAdmin != null && isAdmin);
                request.setAttribute("jwtAuth", auth);

            } catch (Exception e) {
                throw new JwtAuthenticationException("Invalid JWT token");
            }
        }

        chain.doFilter(req, res);
    }
}
