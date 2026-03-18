package com.easytask.backend.controllers;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.dto.RegisterRequest;
import com.easytask.backend.dto.RegisterResponse;
import com.easytask.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        System.out.println("bbbbbbbbbbb");
        RegisterResponse user = userService.register(request);
        System.out.println("aaaaaaaaaaaaa");
        return ResponseEntity.ok(
                new ApiResponse<RegisterResponse>("Ok", user)
        );
    }


}
