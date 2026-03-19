package com.easytask.backend.controllers;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.models.Task;
import com.easytask.backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Task>>> allTask(){
        try {
            List<Task> list = taskService.getAll();
            return ResponseEntity.ok(new ApiResponse<>("Get all tasks success.", list));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    Get a task by ID
//    Create a new task
//    Update an existing task
//    Delete a task
}
