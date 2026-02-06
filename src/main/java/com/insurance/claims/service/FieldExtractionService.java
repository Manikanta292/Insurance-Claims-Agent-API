package com.insurance.claims.service;

import com.insurance.claims.dto.ExtractedFields;
import com.insurance.claims.util.RegexPatterns;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Service for extracting structured fields from raw text using regex patterns
 */
@Service
public class FieldExtractionService {
    
    private static final DateTimeFormatter[] DATE_FORMATTERS = {
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("MM-dd-yyyy"),
        DateTimeFormatter.ofPattern("M/d/yyyy"),
        DateTimeFormatter.ofPattern("M-d-yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yy"),
        DateTimeFormatter.ofPattern("MM-dd-yy")
    };
    
    private static final DateTimeFormatter[] TIME_FORMATTERS = {
        DateTimeFormatter.ofPattern("HH:mm"),
        DateTimeFormatter.ofPattern("h:mm a"),
        DateTimeFormatter.ofPattern("h:mma")
    };
    
    /**
     * Extract all fields from raw document text
     */
    public ExtractedFields extractFields(String rawText) {
        return ExtractedFields.builder()
            .policyNumber(extractPolicyNumber(rawText))
            .policyholderName(extractPolicyholderName(rawText))
            .effectiveStartDate(extractEffectiveStartDate(rawText))
            .effectiveEndDate(extractEffectiveEndDate(rawText))
            .incidentDate(extractIncidentDate(rawText))
            .incidentTime(extractIncidentTime(rawText))
            .location(extractLocation(rawText))
            .description(extractDescription(rawText))
            .claimantName(extractClaimantName(rawText))
            .thirdParties(extractThirdParties(rawText))
            .contactDetails(extractContactDetails(rawText))
            .assetType(extractAssetType(rawText))
            .assetId(extractAssetId(rawText))
            .estimatedDamage(extractEstimatedDamage(rawText))
            .claimType(extractClaimType(rawText))
            .attachments(extractAttachments(rawText))
            .initialEstimate(extractInitialEstimate(rawText))
            .build();
    }
    
    private String extractPolicyNumber(String text) {
        Matcher matcher = RegexPatterns.POLICY_NUMBER.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private String extractPolicyholderName(String text) {
        Matcher matcher = RegexPatterns.POLICYHOLDER_NAME.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private LocalDate extractEffectiveStartDate(String text) {
        Matcher matcher = RegexPatterns.EFFECTIVE_DATES.matcher(text);
        if (matcher.find()) {
            return parseDate(matcher.group(1));
        }
        return null;
    }
    
    private LocalDate extractEffectiveEndDate(String text) {
        Matcher matcher = RegexPatterns.EFFECTIVE_DATES.matcher(text);
        if (matcher.find()) {
            return parseDate(matcher.group(2));
        }
        return null;
    }
    
    private LocalDate extractIncidentDate(String text) {
        Matcher matcher = RegexPatterns.INCIDENT_DATE.matcher(text);
        return matcher.find() ? parseDate(matcher.group(1)) : null;
    }
    
    private LocalTime extractIncidentTime(String text) {
        Matcher matcher = RegexPatterns.INCIDENT_TIME.matcher(text);
        return matcher.find() ? parseTime(matcher.group(1)) : null;
    }
    
    private String extractLocation(String text) {
        Matcher matcher = RegexPatterns.LOCATION.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private String extractDescription(String text) {
        Matcher matcher = RegexPatterns.DESCRIPTION.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private String extractClaimantName(String text) {
        Matcher matcher = RegexPatterns.CLAIMANT_NAME.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private List<String> extractThirdParties(String text) {
        // Simple implementation - can be enhanced
        return new ArrayList<>();
    }
    
    private String extractContactDetails(String text) {
        Matcher matcher = RegexPatterns.CONTACT_DETAILS.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private String extractAssetType(String text) {
        Matcher matcher = RegexPatterns.ASSET_TYPE.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private String extractAssetId(String text) {
        Matcher matcher = RegexPatterns.ASSET_ID.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private BigDecimal extractEstimatedDamage(String text) {
        Matcher matcher = RegexPatterns.ESTIMATED_DAMAGE.matcher(text);
        if (matcher.find()) {
            String amount = matcher.group(1).replace(",", "");
            return new BigDecimal(amount);
        }
        return null;
    }
    
    private String extractClaimType(String text) {
        Matcher matcher = RegexPatterns.CLAIM_TYPE.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
    
    private List<String> extractAttachments(String text) {
        Matcher matcher = RegexPatterns.ATTACHMENTS.matcher(text);
        if (matcher.find()) {
            String attachmentsStr = matcher.group(1).trim();
            return Arrays.asList(attachmentsStr.split("[,;]"));
        }
        return new ArrayList<>();
    }
    
    private BigDecimal extractInitialEstimate(String text) {
        Matcher matcher = RegexPatterns.INITIAL_ESTIMATE.matcher(text);
        if (matcher.find()) {
            String amount = matcher.group(1).replace(",", "");
            return new BigDecimal(amount);
        }
        return null;
    }
    
    private LocalDate parseDate(String dateStr) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }
        return null;
    }
    
    private LocalTime parseTime(String timeStr) {
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                return LocalTime.parse(timeStr.toUpperCase(), formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }
        return null;
    }
}

