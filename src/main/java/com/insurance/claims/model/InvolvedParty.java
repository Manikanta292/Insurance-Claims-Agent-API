package com.insurance.claims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents involved parties in the claim
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvolvedParty {
    private String claimantName;
    private List<String> thirdParties;
    private String contactDetails;
}

