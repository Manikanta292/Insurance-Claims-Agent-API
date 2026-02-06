package com.insurance.claims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents policy information extracted from FNOL documents
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyInformation {
    private String policyNumber;
    private String policyholderName;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
}

