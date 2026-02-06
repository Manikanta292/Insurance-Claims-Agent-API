package com.insurance.claims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application for Insurance Claims Processing Agent
 * 
 * This application provides autonomous processing of FNOL (First Notice of Loss) documents,
 * extracting key fields, validating data, and routing claims to appropriate workflows.
 */
@SpringBootApplication
public class InsuranceClaimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceClaimsApplication.class, args);
    }
}

