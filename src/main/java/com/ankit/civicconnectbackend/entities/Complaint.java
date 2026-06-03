package com.ankit.civicconnectbackend.entities;

import com.ankit.civicconnectbackend.enums.ComplaintStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Complaint title is required")
    @Column(nullable = false)
    private String complaintTitle;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff assignedStaff;

    @NotBlank(message = "Complaint description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String complaintDescription;

    @NotBlank(message = "Complaint category is required")
    @Column(nullable = false)
    private String complaintCategory;

    @Column(nullable = false)
    private String complaintLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus complaintStatus
            = ComplaintStatus.PENDING;

    private LocalDateTime createdAt
            = LocalDateTime.now();
}