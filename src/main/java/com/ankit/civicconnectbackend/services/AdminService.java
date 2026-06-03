package com.ankit.civicconnectbackend.services;

import com.ankit.civicconnectbackend.configs.JwtUtil;
import com.ankit.civicconnectbackend.dto.LoginRequest;
import com.ankit.civicconnectbackend.entities.Admin;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.entities.Staff;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import com.ankit.civicconnectbackend.repos.AdminRepo;
import com.ankit.civicconnectbackend.repos.ComplaintRepo;
import com.ankit.civicconnectbackend.repos.StaffRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final ComplaintRepo complaintRepo;
    private final AdminRepo  adminRepo;
    private final StaffRepo staffRepo;
   private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminService(AdminRepo adminRepo, PasswordEncoder passwordEncoder,JwtUtil jwtUtil,
                        StaffRepo staffRepo, ComplaintRepo complaintRepo) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepo = adminRepo;
        this.complaintRepo = complaintRepo;
        this.staffRepo = staffRepo;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Admin> createAdmin(Admin admin) {
        String encodedPassword = passwordEncoder.encode(admin.getAdminPassword());
        admin.setAdminPassword(encodedPassword);
        return ResponseEntity.ok(adminRepo.save(admin));
    }



    public String loginAdmin(LoginRequest loginRequest) {
        Admin admin = adminRepo.findByAdminEmail(loginRequest.getEmail());
        if (admin == null) {
            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getAdminPassword())) {
            throw new RuntimeException(
                    "Invalid email or password"
            );
        }
        return jwtUtil.generateToken(admin.getAdminEmail());
    }



    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintRepo.findAll());
    }

    public ResponseEntity<Complaint> updateComplaintStatus(Integer complaintId,
                                                           ComplaintStatus status) {

        Complaint complaint = complaintRepo.findById(complaintId).orElseThrow(
                ()-> new RuntimeException("complaint not found")
        );
        complaint.setComplaintStatus(status);
        return ResponseEntity.ok(complaintRepo.save(complaint));
    }
    public ResponseEntity<Complaint> assignComplaint(
            Integer complaintId,
            Integer staffId
    ) {

        Complaint complaint = complaintRepo.findById(
                        complaintId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Complaint not found"));

        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Staff not found"));

        complaint.setAssignedStaff(staff);

        complaint.setComplaintStatus(ComplaintStatus.ASSIGNED);

        return ResponseEntity.ok(
                complaintRepo.save(complaint)
        );
    }
}
