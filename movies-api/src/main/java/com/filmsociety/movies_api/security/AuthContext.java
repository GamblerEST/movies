package com.filmsociety.movies_api.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.filmsociety.movies_api.exception.JwtAuthenticationException;
import com.filmsociety.movies_api.exception.UnauthorizedActionException;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequestScope
public class AuthContext {

    private final JwtAuthentication jwtAuthentication;

    public AuthContext(HttpServletRequest request) {
        Object attr = request.getAttribute("jwtAuth");
        if (attr instanceof JwtAuthentication auth) {
            this.jwtAuthentication = auth;
        } else {
            this.jwtAuthentication = null;
        }
    }

    public Long getUserId() {
        assertAuthenticated();
        return jwtAuthentication.getUserId();
    }

    public boolean isAdmin() {
        assertAuthenticated();
        return jwtAuthentication.isAdmin();
    }

    private void assertAuthenticated() {
        if (jwtAuthentication == null) {
            throw new JwtAuthenticationException("Unauthorized access: Missing or invalid JWT.");
        }
    }

    public void assertAdmin() {
        if (!isAdmin()) {
            throw new UnauthorizedActionException("Unauthorized action: Admin privileges are required.");
        }
    }
}