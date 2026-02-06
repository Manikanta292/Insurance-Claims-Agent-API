package com.insurance.claims.dto;

import com.insurance.claims.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO representing all extracted fields from FNOL document
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractedFields {
    // Policy Information
    private String policyNumber;
    private String policyholderName;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
    
    // Incident Information
    private LocalDate incidentDate;
    private LocalTime incidentTime;
    private String location;
    private String description;
    
    // Involved Parties
    private String claimantName;
    private List<String> thirdParties;
    private String contactDetails;
    
    // Asset Details
    private String assetType;
    private String assetId;
    private BigDecimal estimatedDamage;
    
    // Other Mandatory Fields
    private String claimType;
    private List<String> attachments;
    private BigDecimal initialEstimate;
}

