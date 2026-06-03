package com.ankit.civicconnectbackend.repos;

import com.ankit.civicconnectbackend.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepo extends JpaRepository<Staff,Integer> {
    Staff findByEmail(String email);

}
