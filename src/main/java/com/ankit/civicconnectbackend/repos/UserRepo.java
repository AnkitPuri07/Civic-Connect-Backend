package com.ankit.civicconnectbackend.repos;

import com.ankit.civicconnectbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}

