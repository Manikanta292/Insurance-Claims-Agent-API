package com.insurance.claims.service;

import com.insurance.claims.dto.ExtractedFields;
import com.insurance.claims.util.RegexPatterns;
import com.insurance.claims.util.RoutingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Service for routing claims based on business rules
 */
@Service
public class RoutingService {
    
    @Autowired
    private ValidationService validationService;
    
    /**
     * Determine the recommended route for a claim based on business rules
     * @param extractedFields The extracted fields from FNOL document
     * @return Routing decision with reasoning
     */
    public RoutingDecision determineRoute(ExtractedFields extractedFields) {
        List<String> reasons = new ArrayList<>();
        String route;
        
        // Rule 1: Check for missing mandatory fields (highest priority)
        if (!validationService.hasAllMandatoryFields(extractedFields)) {
            route = RoutingConstants.MANUAL_REVIEW;
            reasons.add("Missing mandatory fields detected");
            return new RoutingDecision(route, String.join("; ", reasons));
        }
        
        // Rule 2: Check for fraud indicators
        if (containsFraudKeywords(extractedFields.getDescription())) {
            route = RoutingConstants.INVESTIGATION_FLAG;
            reasons.add("Description contains fraud-related keywords");
            return new RoutingDecision(route, String.join("; ", reasons));
        }
        
        // Rule 3: Check for injury claims (requires specialist)
        if (isInjuryClaim(extractedFields.getClaimType())) {
            route = RoutingConstants.SPECIALIST_QUEUE;
            reasons.add("Claim type is injury - requires specialist review");
            return new RoutingDecision(route, String.join("; ", reasons));
        }
        
        // Rule 4: Fast-track for low-value claims with all fields present
        if (isFastTrackEligible(extractedFields.getEstimatedDamage())) {
            route = RoutingConstants.FAST_TRACK;
            reasons.add("Estimated damage below $25,000 threshold and all mandatory fields present");
            return new RoutingDecision(route, String.join("; ", reasons));
        }
        
        // Default: Manual review for high-value claims
        route = RoutingConstants.MANUAL_REVIEW;
        reasons.add("Estimated damage exceeds fast-track threshold - requires manual review");
        return new RoutingDecision(route, String.join("; ", reasons));
    }
    
    /**
     * Check if description contains fraud-related keywords
     */
    private boolean containsFraudKeywords(String description) {
        if (description == null || description.isEmpty()) {
            return false;
        }
        
        Matcher matcher = RegexPatterns.FRAUD_KEYWORDS.matcher(description.toLowerCase());
        return matcher.find();
    }
    
    /**
     * Check if claim type is injury
     */
    private boolean isInjuryClaim(String claimType) {
        if (claimType == null) {
            return false;
        }
        return claimType.toLowerCase().contains(RoutingConstants.CLAIM_TYPE_INJURY);
    }
    
    /**
     * Check if claim is eligible for fast-track processing
     */
    private boolean isFastTrackEligible(BigDecimal estimatedDamage) {
        if (estimatedDamage == null) {
            return false;
        }
        return estimatedDamage.compareTo(RoutingConstants.FAST_TRACK_THRESHOLD) < 0;
    }
    
    /**
     * Inner class to hold routing decision and reasoning
     */
    public static class RoutingDecision {
        private final String route;
        private final String reasoning;
        
        public RoutingDecision(String route, String reasoning) {
            this.route = route;
            this.reasoning = reasoning;
        }
        
        public String getRoute() {
            return route;
        }
        
        public String getReasoning() {
            return reasoning;
        }
    }
}

