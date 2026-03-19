package com.easytask.backend.controllers;


import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.UserRepository;
import com.easytask.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    ResponseEntity<ApiResponse<Map<String, String>>> getUser(){
        try {
            Users user = userService.getUser();
            Map<String, String> profile = new HashMap<>();
            profile.put("username", user.getUsername());
            profile.put("id", user.getId().toString());

            return ResponseEntity.ok(new ApiResponse<>("success", profile));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        }
    }
}
