package com.insurance.claims.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.claims.model.StateFraudPenalty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing state-specific fraud penalty information
 */
@Service
public class FraudPenaltyService {
    
    private Map<String, StateFraudPenalty> fraudPenaltyMap = new HashMap<>();
    
    /**
     * Load fraud penalty data from JSON file on application startup
     */
    @PostConstruct
    public void loadFraudPenalties() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("fraud-penalties.json");
            
            List<StateFraudPenalty> penalties = mapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<StateFraudPenalty>>() {}
            );
            
            // Build map for quick lookup by state code
            for (StateFraudPenalty penalty : penalties) {
                fraudPenaltyMap.put(penalty.getStateCode().toUpperCase(), penalty);
            }
            
        } catch (IOException e) {
            // Log error but don't fail application startup
            System.err.println("Warning: Could not load fraud penalties data: " + e.getMessage());
        }
    }
    
    /**
     * Get fraud penalty information for a specific state
     * @param stateCode Two-letter state code (e.g., "CA", "NY")
     * @return StateFraudPenalty or null if not found
     */
    public StateFraudPenalty getPenaltyByState(String stateCode) {
        if (stateCode == null || stateCode.isEmpty()) {
            return null;
        }
        return fraudPenaltyMap.get(stateCode.toUpperCase());
    }
    
    /**
     * Get all fraud penalties
     */
    public List<StateFraudPenalty> getAllPenalties() {
        return fraudPenaltyMap.values().stream().toList();
    }
    
    /**
     * Check if a state has criminal penalties for fraud
     */
    public boolean hasCriminalPenalty(String stateCode) {
        StateFraudPenalty penalty = getPenaltyByState(stateCode);
        return penalty != null && penalty.isHasCriminalPenalty();
    }
    
    /**
     * Check if a state has civil penalties for fraud
     */
    public boolean hasCivilPenalty(String stateCode) {
        StateFraudPenalty penalty = getPenaltyByState(stateCode);
        return penalty != null && penalty.isHasCivilPenalty();
    }
}

