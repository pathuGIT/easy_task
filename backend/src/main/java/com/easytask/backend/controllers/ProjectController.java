package com.easytask.backend.controllers;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.models.Project;
import com.easytask.backend.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping()
    public ResponseEntity<ApiResponse> add(Authentication authentication, @Valid @RequestBody Project project){
        try {
            ApiResponse response = projectService.addProject(project, authentication);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
