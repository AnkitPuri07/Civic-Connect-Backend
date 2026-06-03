package com.ankit.civicconnectbackend.controllers;

import com.ankit.civicconnectbackend.dto.ComplaintRequest;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.services.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/complaints")
public class ComplaintController {

private final ComplaintService complaintService;

public ComplaintController(ComplaintService complaintService) {
    this.complaintService = complaintService;

}

@PostMapping
public ResponseEntity<Complaint> addComplaint(
        @Valid
        @RequestBody ComplaintRequest complaintRequest) {
    return complaintService.addComplaint(complaintRequest);
}

@GetMapping("/my")
public ResponseEntity<List<Complaint>> findByUserId() {
    return ResponseEntity.ok(
            complaintService.getMyComplaints()
    );
}

@PutMapping("/{complaintId}/{userId}")
public ResponseEntity<Complaint> updateComplaint(
        @PathVariable Integer complaintId,
        @PathVariable Integer userId,
        @Valid
        @RequestBody ComplaintRequest complaintRequest
) {

    return complaintService.updateComplaint(
            complaintId,
            userId,
            complaintRequest
    );
}

@DeleteMapping("/{complaintId}/user/{userId}")
public ResponseEntity<String> deleteComplaint(
        @PathVariable Integer complaintId,
        @PathVariable Integer userId
) {

    return complaintService.deleteComplaint(
            complaintId,
            userId

    );
}
}
