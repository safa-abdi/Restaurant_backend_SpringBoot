package com.restaurant.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.AuthResponse;
import com.restaurant.dto.LoginRequest;
import com.restaurant.entities.User;
import com.restaurant.entities.User.Role;
import com.restaurant.security.JwtUtil;
import com.restaurant.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Role role = user.getRole();  

            String token = jwtUtil.generateToken(user.getEmail(), role);  

            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity.status(401).body("Invalid email or password");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            jwtUtil.revokeToken(token); 

            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logout successful");
        }

        return ResponseEntity.badRequest().body("Invalid token");
    }
}
