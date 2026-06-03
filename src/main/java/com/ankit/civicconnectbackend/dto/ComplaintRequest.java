package com.ankit.civicconnectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintRequest {

    @NotBlank(message = "must enter complaint description")
    private String complaintDescription;

    @NotBlank(message = "must enter complaint title")
    private String complaintTitle;

    @NotBlank(message = "must enter complaint location")
    private String complaintLocation;

    @NotBlank(message = "must enter complaint category")
    private String complaintCategory;
}