package com.insurance.claims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing the output of claim processing
 * Matches the required JSON format:
 * {
 *   "extractedFields": {},
 *   "missingFields": [],
 *   "recommendedRoute": "",
 *   "reasoning": ""
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimExtractionResult {
    private ExtractedFields extractedFields;
    private List<String> missingFields;
    private String recommendedRoute;
    private String reasoning;
}

