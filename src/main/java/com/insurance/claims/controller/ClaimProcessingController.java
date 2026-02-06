package com.insurance.claims.controller;

import com.insurance.claims.dto.ClaimExtractionResult;
import com.insurance.claims.dto.ExtractedFields;
import com.insurance.claims.service.DocumentParserService;
import com.insurance.claims.service.FieldExtractionService;
import com.insurance.claims.service.RoutingService;
import com.insurance.claims.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST Controller for processing insurance claims (FNOL documents)
 */
@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimProcessingController {
    
    @Autowired
    private DocumentParserService documentParserService;
    
    @Autowired
    private FieldExtractionService fieldExtractionService;
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private RoutingService routingService;
    
    /**
     * Process FNOL document and return extraction results with routing recommendation
     * 
     * @param file The FNOL document (PDF or TXT format)
     * @return ClaimExtractionResult with extracted fields, missing fields, route, and reasoning
     */
    @PostMapping("/process")
    public ResponseEntity<?> processClaimDocument(@RequestParam("file") MultipartFile file) {
        try {
            // Step 1: Parse document to extract raw text
            String rawText = documentParserService.parseDocument(file);
            
            // Step 2: Extract structured fields from raw text
            ExtractedFields extractedFields = fieldExtractionService.extractFields(rawText);
            
            // Step 3: Identify missing mandatory fields
            List<String> missingFields = validationService.identifyMissingFields(extractedFields);
            
            // Step 4: Determine routing based on business rules
            RoutingService.RoutingDecision decision = routingService.determineRoute(extractedFields);
            
            // Step 5: Build response
            ClaimExtractionResult result = ClaimExtractionResult.builder()
                    .extractedFields(extractedFields)
                    .missingFields(missingFields)
                    .recommendedRoute(decision.getRoute())
                    .reasoning(decision.getReasoning())
                    .build();
            
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing document: " + e.getMessage());
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Insurance Claims Processing Agent is running");
    }
}

