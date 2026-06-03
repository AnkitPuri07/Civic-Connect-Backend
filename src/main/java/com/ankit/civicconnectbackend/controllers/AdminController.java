package com.ankit.civicconnectbackend.controllers;

import com.ankit.civicconnectbackend.dto.LoginRequest;
import com.ankit.civicconnectbackend.entities.Admin;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import com.ankit.civicconnectbackend.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
private final AdminService adminService;

public AdminController(AdminService adminService) {
    this.adminService = adminService;
}

@PostMapping("/register")
public ResponseEntity<Admin> registerAdmin(
        @Valid
        @RequestBody Admin admin) {

    return adminService.createAdmin(admin);
}

@PostMapping("/login")
public ResponseEntity<?> loginAdmin(
        @Valid
        @RequestBody LoginRequest loginRequest) {

    return ResponseEntity.ok(
            Map.of("token", adminService.loginAdmin(loginRequest))
    );
}

@GetMapping("/complaints")
public ResponseEntity<List<Complaint>>
getAllComplaints() {

    return adminService.getAllComplaints();
}

@PutMapping("/complaints/{complaintId}/status")
public ResponseEntity<Complaint>
updateComplaintStatus(@PathVariable Integer complaintId,
                      @RequestParam ComplaintStatus status) {
    return adminService.updateComplaintStatus(complaintId, status);

}


@PutMapping("/complaints/{complaintId}/assign/{staffId}")
public ResponseEntity<Complaint> assignComplaint(
        @PathVariable Integer complaintId,
        @PathVariable Integer staffId
) {

    return adminService.assignComplaint(
            complaintId,
            staffId
    );
}
}
