package com.ankit.civicconnectbackend.repos;

import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.entities.Staff;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepo extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByUserId(Integer userId);
    List<Complaint> findByAssignedStaff(Staff assignedStaff);

    List<Complaint> findByComplaintStatus(ComplaintStatus complaintStatus);
}
