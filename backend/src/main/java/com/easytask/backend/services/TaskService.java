package com.easytask.backend.services;

import com.easytask.backend.models.Task;
import com.easytask.backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    private Authentication authentication = null;

    public List<Task> getAll() {
        String un = authentication.getName();
        return null;
    }
}
