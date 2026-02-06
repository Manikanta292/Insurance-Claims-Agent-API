package com.insurance.claims.util;

import java.util.regex.Pattern;

/**
 * Centralized regex patterns for extracting fields from FNOL documents
 */
public class RegexPatterns {
    
    // Policy Information Patterns
    public static final Pattern POLICY_NUMBER = Pattern.compile(
        "(?i)policy\\s*(?:number|#|no\\.?)\\s*:?\\s*([A-Z0-9-]+)", 
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern POLICYHOLDER_NAME = Pattern.compile(
        "(?i)policyholder\\s*(?:name)?\\s*:?\\s*([A-Za-z\\s.]+?)(?=\\n|Policy|Incident|$)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern EFFECTIVE_DATES = Pattern.compile(
        "(?i)effective\\s*(?:date|period)?\\s*:?\\s*(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4})\\s*(?:to|-|through)\\s*(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4})",
        Pattern.CASE_INSENSITIVE
    );
    
    // Incident Information Patterns
    public static final Pattern INCIDENT_DATE = Pattern.compile(
        "(?i)(?:incident|loss|accident)\\s*date\\s*:?\\s*(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4})",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern INCIDENT_TIME = Pattern.compile(
        "(?i)(?:incident|loss|accident)\\s*time\\s*:?\\s*(\\d{1,2}:\\d{2}\\s*(?:AM|PM)?)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern LOCATION = Pattern.compile(
        "(?i)location\\s*:?\\s*([^\\n]+?)(?=\\n|Description|$)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern DESCRIPTION = Pattern.compile(
        "(?i)(?:incident\\s*)?description\\s*:?\\s*([^\\n]+(?:\\n(?!\\w+:)[^\\n]+)*)",
        Pattern.CASE_INSENSITIVE
    );
    
    // Involved Parties Patterns
    public static final Pattern CLAIMANT_NAME = Pattern.compile(
        "(?i)claimant\\s*(?:name)?\\s*:?\\s*([A-Za-z\\s.]+?)(?=\\n|Contact|Third|$)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern CONTACT_DETAILS = Pattern.compile(
        "(?i)contact\\s*(?:details|info)?\\s*:?\\s*([^\\n]+)",
        Pattern.CASE_INSENSITIVE
    );
    
    // Asset Details Patterns
    public static final Pattern ASSET_TYPE = Pattern.compile(
        "(?i)asset\\s*type\\s*:?\\s*([A-Za-z\\s]+?)(?=\\n|Asset ID|$)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern ASSET_ID = Pattern.compile(
        "(?i)asset\\s*(?:id|identifier)\\s*:?\\s*([A-Z0-9-]+)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern ESTIMATED_DAMAGE = Pattern.compile(
        "(?i)estimated\\s*damage\\s*:?\\s*\\$?\\s*([\\d,]+(?:\\.\\d{2})?)",
        Pattern.CASE_INSENSITIVE
    );
    
    // Other Mandatory Fields
    public static final Pattern CLAIM_TYPE = Pattern.compile(
        "(?i)claim\\s*type\\s*:?\\s*([A-Za-z\\s]+?)(?=\\n|Attachments|$)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern INITIAL_ESTIMATE = Pattern.compile(
        "(?i)initial\\s*estimate\\s*:?\\s*\\$?\\s*([\\d,]+(?:\\.\\d{2})?)",
        Pattern.CASE_INSENSITIVE
    );
    
    public static final Pattern ATTACHMENTS = Pattern.compile(
        "(?i)attachments?\\s*:?\\s*([^\\n]+)",
        Pattern.CASE_INSENSITIVE
    );
    
    // Fraud Detection Keywords
    public static final Pattern FRAUD_KEYWORDS = Pattern.compile(
        "(?i)\\b(fraud|fraudulent|inconsistent|staged|suspicious|fake|fabricated)\\b",
        Pattern.CASE_INSENSITIVE
    );
}

