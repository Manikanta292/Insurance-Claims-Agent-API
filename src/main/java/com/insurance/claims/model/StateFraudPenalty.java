package com.insurance.claims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents state-specific fraud penalty information from ACORD forms
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateFraudPenalty {
    private String state;
    private String stateCode;
    private String penaltyDescription;
    private boolean hasCriminalPenalty;
    private boolean hasCivilPenalty;
}

