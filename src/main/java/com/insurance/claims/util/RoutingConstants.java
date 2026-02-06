package com.insurance.claims.util;

import java.math.BigDecimal;

/**
 * Constants for claim routing decisions
 */
public class RoutingConstants {
    
    // Routing Destinations
    public static final String FAST_TRACK = "Fast-track";
    public static final String MANUAL_REVIEW = "Manual review";
    public static final String INVESTIGATION_FLAG = "Investigation Flag";
    public static final String SPECIALIST_QUEUE = "Specialist Queue";
    
    // Routing Thresholds
    public static final BigDecimal FAST_TRACK_THRESHOLD = new BigDecimal("25000.00");
    
    // Claim Types
    public static final String CLAIM_TYPE_INJURY = "injury";
    
    // Mandatory Fields
    public static final String[] MANDATORY_FIELDS = {
        "policyNumber",
        "policyholderName",
        "incidentDate",
        "location",
        "description",
        "claimantName",
        "contactDetails",
        "assetType",
        "estimatedDamage",
        "claimType"
    };
    
    private RoutingConstants() {
        // Utility class - prevent instantiation
    }
}

