package com.ankit.civicconnectbackend.services;

import com.ankit.civicconnectbackend.configs.JwtUtil;
import com.ankit.civicconnectbackend.dto.LoginRequest;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.entities.Staff;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import com.ankit.civicconnectbackend.repos.ComplaintRepo;
import com.ankit.civicconnectbackend.repos.StaffRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final ComplaintRepo complaintRepo;
    private final StaffRepo staffRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public StaffService(StaffRepo staffRepo, PasswordEncoder passwordEncoder, ComplaintRepo complaintRepo
    , JwtUtil jwtUtil
    ) {
        this.passwordEncoder = passwordEncoder;
        this.staffRepo = staffRepo;
        this.complaintRepo = complaintRepo;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Staff> createStaff(Staff staff) {
        String encodedPassword = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(encodedPassword);
        return ResponseEntity.ok(staffRepo.save(staff));
    }

    public String loginStaffByEmail(LoginRequest loginRequest) {
        Staff staff = staffRepo.findByEmail(loginRequest.getEmail());
        if(staff==null){
            throw new RuntimeException(
                    "Invalid email or password"
            );        }
        if(!passwordEncoder.matches(loginRequest.getPassword(),staff.getPassword())){
            throw new RuntimeException(
                    "Invalid email or password"
            );         }
        return jwtUtil.generateToken(staff.getEmail());
    }

    public ResponseEntity<List<Complaint>> getComplaintsByStaffId(int staffId) {
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(()-> new RuntimeException("Staff not found"));

        return ResponseEntity.ok(complaintRepo.findByAssignedStaff(staff));
    }
    public ResponseEntity<Complaint>
    updateComplaintStatusByStaff(

            Integer complaintId,
            Integer staffId,
          ComplaintStatus status
    ) {

        Complaint complaint = complaintRepo.findById(
                        complaintId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Complaint not found"));

        if (complaint.getAssignedStaff() == null) {
            throw new RuntimeException(
                    "Complaint not assigned");
        }

        if (!complaint.getAssignedStaff()
                .getId()
                .equals(staffId)) {

            throw new RuntimeException(
                    "You are not assigned to this complaint");
        }

        complaint.setComplaintStatus(status);

        return ResponseEntity.ok(
                complaintRepo.save(complaint)
        );
    }

    public List<Staff> getEveryStaff() {
        return staffRepo.findAll();
    }
}
