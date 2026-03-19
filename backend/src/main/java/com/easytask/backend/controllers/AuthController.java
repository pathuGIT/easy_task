package com.easytask.backend.controllers;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.dto.LoginRequest;
import com.easytask.backend.dto.LoginResponse;
import com.easytask.backend.dto.RegisterResponse;
import com.easytask.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody LoginRequest request) {
        try {
            RegisterResponse user = userService.register(request);
            return ResponseEntity.ok(
                    new ApiResponse<>("Registration Success.", user)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Login as a user
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse loginResponse = userService.login(loginRequest);
            System.out.println("T0");
            return ResponseEntity.ok(new ApiResponse<>("Login Success.", loginResponse));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        }
    }

    // Refresh the new token
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestBody Map<String, String> request){
        try {
            String refreshToken = request.get("refreshToken");
            Map<String, String> active_token = userService.refresh(refreshToken);
            return ResponseEntity.ok(new ApiResponse<>("Login Success.", active_token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        }
    }

    // user logout
    @PutMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            //String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Map<String, String> res = userService.logout();
            return ResponseEntity.ok(new ApiResponse<>("Logout successful", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(),null));
        }
    }

}
