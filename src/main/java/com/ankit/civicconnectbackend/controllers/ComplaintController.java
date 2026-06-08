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

    @PutMapping("/{complaintId}")
    public ResponseEntity<Complaint> updateComplaint(
            @PathVariable Integer complaintId,
            @Valid @RequestBody ComplaintRequest complaintRequest
    ) {

        return ResponseEntity.ok(
                complaintService.updateComplaint(
                        complaintId,
                        complaintRequest
                )
        );
    }

    @DeleteMapping("/{complaintId}")
    public ResponseEntity<String> deleteComplaint(
            @PathVariable Integer complaintId
    ) {
        return ResponseEntity.ok(
                complaintService.deleteComplaint(complaintId)
        );
    }
}
