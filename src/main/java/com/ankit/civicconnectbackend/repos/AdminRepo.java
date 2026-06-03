package com.ankit.civicconnectbackend.repos;

import com.ankit.civicconnectbackend.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {
 Admin findByAdminEmail(String email);
}
