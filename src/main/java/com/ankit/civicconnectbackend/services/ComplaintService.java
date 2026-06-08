package com.ankit.civicconnectbackend.services;
import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.ankit.civicconnectbackend.dto.ComplaintRequest;
import com.ankit.civicconnectbackend.entities.Complaint;
import com.ankit.civicconnectbackend.entities.User;
import com.ankit.civicconnectbackend.repos.ComplaintRepo;
import com.ankit.civicconnectbackend.repos.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        User user = userRepo.findByEmail(email);


        return complaintRepo.findByUserId(user.getId());
    }


    public Complaint updateComplaint(
            Integer complaintId,
            ComplaintRequest complaintRequest
    ) {

        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!complaint.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You cannot update this complaint"
            );
        }

        complaint.setComplaintTitle(
                complaintRequest.getComplaintTitle()
        );

        complaint.setComplaintDescription(
                complaintRequest.getComplaintDescription()
        );

        complaint.setComplaintCategory(
                complaintRequest.getComplaintCategory()
        );

        complaint.setComplaintLocation(
                complaintRequest.getComplaintLocation()
        );

        return complaintRepo.save(complaint);
    }

    public String deleteComplaint(Integer complaintId) {

        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!complaint.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot delete this complaint"
            );
        }

        complaintRepo.delete(complaint);

        return "Complaint deleted successfully";
    }

    public ResponseEntity<List<Complaint>> getComplaintsByStatus(ComplaintStatus status) {
        return ResponseEntity.ok(complaintRepo.findByComplaintStatus(status));
    }
}
