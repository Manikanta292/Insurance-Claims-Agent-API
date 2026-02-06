package com.insurance.claims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents asset details in the claim
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetDetails {
    private String assetType;
    private String assetId;
    private BigDecimal estimatedDamage;
}

