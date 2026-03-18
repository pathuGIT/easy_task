package com.easytask.backend.controllers;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.dto.LoginRequest;
import com.easytask.backend.dto.LoginResponse;
import com.easytask.backend.dto.RegisterResponse;
import com.easytask.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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


}
