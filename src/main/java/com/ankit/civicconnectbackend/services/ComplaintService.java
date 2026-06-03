package com.ankit.civicconnectbackend.services;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.ankit.civicconnectbackend.dto.ComplaintRequest;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.entities.User;
import com.ankit.civicconnectbackend.repos.ComplaintRepo;
import com.ankit.civicconnectbackend.repos.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {
    private final ComplaintRepo complaintRepo;
    private final UserRepo userRepo;

    public ComplaintService(ComplaintRepo complaintRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.complaintRepo = complaintRepo;
    }

    public ResponseEntity<Complaint> addComplaint(
            ComplaintRequest complaintRequest
    ) {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Complaint complaint = new Complaint();
        complaint.setComplaintCategory(
                complaintRequest.getComplaintCategory()
        );
        complaint.setComplaintDescription(
                complaintRequest.getComplaintDescription()
        );
        complaint.setComplaintLocation(
                complaintRequest.getComplaintLocation()
        );
        complaint.setComplaintTitle(
                complaintRequest.getComplaintTitle()
        );
        complaint.setComplaintStatus(
                ComplaintStatus.PENDING
        );
        complaint.setUser(user);
        complaintRepo.save(complaint);
        return ResponseEntity.ok(complaint);
    }

    public List<Complaint> getMyComplaints() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        System.out.println("MY COMPLAINTS ENDPOINT HIT");
        User user = userRepo.findByEmail(email);


        return complaintRepo.findByUserId(user.getId());
    }
    public ResponseEntity<Complaint> updateComplaint(
            Integer complaintId,
            Integer userId,
            ComplaintRequest complaintRequest
    ) {

        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        if (!complaint.getUser().getId().equals(userId)) {
            throw new RuntimeException(
                    "You cannot update this complaint");
        }

        complaint.setComplaintTitle(
                complaintRequest.getComplaintTitle());

        complaint.setComplaintDescription(
                complaintRequest.getComplaintDescription());

        complaint.setComplaintCategory(
                complaintRequest.getComplaintCategory());

        complaintRepo.save(complaint);

        return ResponseEntity.ok(complaint);
    }

    public ResponseEntity<String> deleteComplaint(Integer complaintId, Integer userId) {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        if (!complaint.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot delete this complaint");
        }
        complaintRepo.delete(complaint);
        return ResponseEntity.ok("Complaint deleted successfully");
    }
}
