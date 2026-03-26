package com.filmsociety.movies_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filmsociety.movies_api.dto.LoginRequest;
import com.filmsociety.movies_api.dto.LoginResponse;
import com.filmsociety.movies_api.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if ("kooduser".equals(request.getUsername()) && "mikkmerila".equals(request.getPassword())) {
            String token = JwtUtil.generateToken(1L, true);
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
