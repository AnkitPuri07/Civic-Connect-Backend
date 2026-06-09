package com.ankit.civicconnectbackend.controllers;

import com.ankit.civicconnectbackend.dto.LoginRequest;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.entities.Staff;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import com.ankit.civicconnectbackend.services.StaffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

private final StaffService staffService;

public StaffController(StaffService staffService) {
    this.staffService = staffService;
}

@PostMapping("/register")
public ResponseEntity<Staff> registerStaff(
        @Valid
        @RequestBody Staff staff) {
    return staffService.createStaff(staff);
}

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                staffService.loginStaffByEmail(request)
        );
    }

@GetMapping("/{staffId}/assignedComplaints")
public ResponseEntity<List<Complaint>> getComplaintsByStaffId(@PathVariable int staffId) {
    return staffService.getComplaintsByStaffId(staffId);
}

@PutMapping("/{staffId}/complaints/{complaintId}/status")
public ResponseEntity<Complaint>
updateComplaintStatusByStaff(

        @PathVariable Integer staffId,
        @PathVariable Integer complaintId,
        @RequestParam ComplaintStatus status
) {

    return staffService
            .updateComplaintStatusByStaff(
                    complaintId,
                    staffId,
                    status
            );
}
}
