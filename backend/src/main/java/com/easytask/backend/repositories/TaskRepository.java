package com.easytask.backend.repositories;

import com.easytask.backend.models.Task;
import com.easytask.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUsers(Users user);

    Task findByIdAndUsers(Long id, Users user);
}
