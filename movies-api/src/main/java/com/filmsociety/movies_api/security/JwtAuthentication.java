package com.filmsociety.movies_api.security;

public class JwtAuthentication {

    private final Long userId;
    private final boolean isAdmin;

    public JwtAuthentication(Long userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
