package com.easytask.backend.repositories;

import com.easytask.backend.dto.LoadUser;
import com.easytask.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    LoadUser findByUsername(String username);

    boolean existsByUsername(String username);
}
