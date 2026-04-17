package com.easytask.backend.controllers;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.models.Task;
import com.easytask.backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks for a project
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Task>>> allTask(@RequestParam long projectId) {
        try {
            List<Task> list = taskService.getAll(projectId);
            return ResponseEntity.ok(new ApiResponse<>("Get all tasks success.", list));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        }
    }

    // Get a task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long taskId, Authentication authentication) {
        try {
            Task task = taskService.getTaskById(taskId, authentication);
            return ResponseEntity.ok(new ApiResponse<>("Get task success.", task));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        } catch (RuntimeException d){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(d.getMessage(),null));
        }
    }

    // Create a new task
    @PostMapping("/create/{projectId}")
    public ResponseEntity<ApiResponse<Task>> createTask(@PathVariable Long projectId, @RequestBody Task task, Authentication authentication) {
        try {
            Task createdTask = taskService.createTask(projectId, task, authentication);
            return ResponseEntity.ok(new ApiResponse<>("Create task success.", createdTask));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(),null));
        }
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id, @RequestBody Task task, Authentication authentication) {
        try {
            Task updatedTask = taskService.updateTask(id, task, authentication);
            return ResponseEntity.ok(new ApiResponse<>("Update task success.", updatedTask));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        } catch (RuntimeException d){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(d.getMessage(),null));
        }
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id, Authentication authentication) {
        try {
            taskService.deleteTask(id, authentication);
            return ResponseEntity.ok(new ApiResponse<>("Delete task success.", "Deleted task id: " + id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(),null));
        } catch (RuntimeException d){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(d.getMessage(),null));
        }
    }
}
