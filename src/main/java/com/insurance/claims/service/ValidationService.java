package com.insurance.claims.service;

import com.insurance.claims.dto.ExtractedFields;
import com.insurance.claims.util.RoutingConstants;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for validating extracted fields and identifying missing mandatory fields
 */
@Service
public class ValidationService {
    
    /**
     * Identify missing mandatory fields from extracted data
     * @param extractedFields The extracted fields from FNOL document
     * @return List of missing mandatory field names
     */
    public List<String> identifyMissingFields(ExtractedFields extractedFields) {
        List<String> missingFields = new ArrayList<>();
        
        for (String fieldName : RoutingConstants.MANDATORY_FIELDS) {
            if (isFieldMissing(extractedFields, fieldName)) {
                missingFields.add(fieldName);
            }
        }
        
        return missingFields;
    }
    
    /**
     * Check if a specific field is missing or null
     */
    private boolean isFieldMissing(ExtractedFields extractedFields, String fieldName) {
        try {
            Field field = ExtractedFields.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(extractedFields);
            return value == null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // If field doesn't exist or can't be accessed, consider it missing
            return true;
        }
    }
    
    /**
     * Check if all mandatory fields are present
     */
    public boolean hasAllMandatoryFields(ExtractedFields extractedFields) {
        return identifyMissingFields(extractedFields).isEmpty();
    }
}

