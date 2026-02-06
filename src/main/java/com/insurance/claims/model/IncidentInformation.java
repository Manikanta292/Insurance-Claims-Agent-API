package com.insurance.claims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents incident information extracted from FNOL documents
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentInformation {
    private LocalDate incidentDate;
    private LocalTime incidentTime;
    private String location;
    private String description;
}

