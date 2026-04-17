package com.easytask.backend.repositories;

import com.easytask.backend.models.Project;
import com.easytask.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Users findByUsers(Users user);
}
