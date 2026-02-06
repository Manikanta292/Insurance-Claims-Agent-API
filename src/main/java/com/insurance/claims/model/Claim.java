package com.insurance.claims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a complete FNOL claim with all extracted information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    private String claimId;
    private PolicyInformation policyInformation;
    private IncidentInformation incidentInformation;
    private InvolvedParty involvedParty;
    private AssetDetails assetDetails;
    private String claimType;
    private List<String> attachments;
    private BigDecimal initialEstimate;
}

