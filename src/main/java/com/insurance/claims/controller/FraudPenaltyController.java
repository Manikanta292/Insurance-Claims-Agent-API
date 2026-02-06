package com.insurance.claims.controller;

import com.insurance.claims.model.StateFraudPenalty;
import com.insurance.claims.service.FraudPenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for accessing state-specific fraud penalty information
 */
@RestController
@RequestMapping("/api/fraud-penalties")
@CrossOrigin(origins = "*")
public class FraudPenaltyController {
    
    @Autowired
    private FraudPenaltyService fraudPenaltyService;
    
    /**
     * Get fraud penalty information for a specific state
     * 
     * @param stateCode Two-letter state code (e.g., "CA", "NY")
     * @return StateFraudPenalty or 404 if not found
     */
    @GetMapping("/{stateCode}")
    public ResponseEntity<?> getPenaltyByState(@PathVariable String stateCode) {
        StateFraudPenalty penalty = fraudPenaltyService.getPenaltyByState(stateCode);
        
        if (penalty == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(penalty);
    }
    
    /**
     * Get all fraud penalties for all states
     */
    @GetMapping
    public ResponseEntity<List<StateFraudPenalty>> getAllPenalties() {
        List<StateFraudPenalty> penalties = fraudPenaltyService.getAllPenalties();
        return ResponseEntity.ok(penalties);
    }
    
    /**
     * Check if a state has criminal penalties for fraud
     */
    @GetMapping("/{stateCode}/has-criminal")
    public ResponseEntity<Boolean> hasCriminalPenalty(@PathVariable String stateCode) {
        boolean hasCriminal = fraudPenaltyService.hasCriminalPenalty(stateCode);
        return ResponseEntity.ok(hasCriminal);
    }
    
    /**
     * Check if a state has civil penalties for fraud
     */
    @GetMapping("/{stateCode}/has-civil")
    public ResponseEntity<Boolean> hasCivilPenalty(@PathVariable String stateCode) {
        boolean hasCivil = fraudPenaltyService.hasCivilPenalty(stateCode);
        return ResponseEntity.ok(hasCivil);
    }
}

