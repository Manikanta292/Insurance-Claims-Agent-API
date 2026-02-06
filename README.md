# Insurance Claims Processing Agent

## Overview

An autonomous Java Spring Boot application that processes First Notice of Loss (FNOL) documents, extracts key information, validates data completeness, and intelligently routes claims to appropriate workflows.

##Features

- **Document Parsing**: Supports PDF and TXT format FNOL documents
- **Field Extraction**: Uses regex patterns to extract structured data from unstructured text
- **Validation**: Identifies missing or inconsistent mandatory fields
- **Intelligent Routing**: Applies business rules to route claims to appropriate queues
- **Fraud Detection**: Integrates state-specific fraud penalty data from ACORD forms
- **RESTful API**: Clean REST endpoints for document processing and fraud penalty lookup

## Architecture

### Layered Architecture
```
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
Utility Layer (Regex Patterns, Constants)
    ↓
Model/DTO Layer (Data Structures)
```

### Components

**Controllers:**
- `ClaimProcessingController` - Main API for processing FNOL documents
- `FraudPenaltyController` - API for state fraud penalty lookup

**Services:**
- `DocumentParserService` - Parses PDF/TXT files to extract raw text
- `FieldExtractionService` - Extracts structured fields using regex
- `ValidationService` - Identifies missing mandatory fields
- `RoutingService` - Applies business rules for claim routing
- `FraudPenaltyService` - Manages state-specific fraud penalty data

**Models:**
- Domain models: `Claim`, `PolicyInformation`, `IncidentInformation`, `InvolvedParty`, `AssetDetails`, `StateFraudPenalty`
- DTOs: `ExtractedFields`, `ClaimExtractionResult`

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd insurance-claims-agent
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

### Verify Installation
```bash
curl http://localhost:8081/api/claims/health
```

Expected response: `Insurance Claims Processing Agent is running`

## API Usage

### Process FNOL Document

**Endpoint:** `POST /api/claims/process`

**Request:**
```bash
curl -X POST http://localhost:8081/api/claims/process \
  -F "file=@sample-documents/fnol-fasttrack.txt"
```

**Response:**
```json
{
  "extractedFields": {
    "policyNumber": "POL-2024-001234",
    "policyholderName": "John Michael Smith",
    "effectiveStartDate": null,
    "effectiveEndDate": null,
    "incidentDate": "2024-03-15",
    "incidentTime": null,
    "location": "123 Main Street, Springfield, IL 62701",
    "description": "Minor rear-end collision at traffic light. Vehicle was stopped at red light when another vehicle failed to brake in time and collided with rear bumper. Low-speed impact, minimal damage to bumper and tail light assembly.",
    "claimantName": "John Michael Smith",
    "thirdParties": [],
    "contactDetails": "Phone: (555) 123-4567, Email: john.smith@email.com",
    "assetType": "Vehicle",
    "assetId": "VIN-1HGBH41JXMN109186",
    "estimatedDamage": 3500,
    "claimType": "property",
    "attachments": [
      "photos_rear_damage.jpg",
      " police_report_IL_2024_0315.pdf"
    ],
    "initialEstimate": 3500
  },
  "missingFields": [],
  "recommendedRoute": "Fast-track",
  "reasoning": "Estimated damage below $25,000 threshold and all mandatory fields present"
}
```

### Get Fraud Penalty by State

**Endpoint:** `GET /api/fraud-penalties/{stateCode}`

**Request:**
```bash
curl http://localhost:8081/api/fraud-penalties/MH
```

**Response:**
```json
{
  "state": "Maharashtra",
  "stateCode": "MH",
  "penaltyDescription": "Insurance fraud, including submission of false or misleading claims, is punishable under applicable provisions of the IPC and regulatory laws, and may result in fines, imprisonment, and blacklisting by insurers.",
  "hasCriminalPenalty": true,
  "hasCivilPenalty": true
}
```

### Get All Fraud Penalties

**Endpoint:** `GET /api/fraud-penalties`

```bash
curl http://localhost:8081/api/fraud-penalties
```
```json
[
  {
    "state": "Telangana",
    "stateCode": "TG",
    "penaltyDescription": "Any person who knowingly submits a false or fraudulent insurance claim may be prosecuted under applicable provisions of the Indian Penal Code (IPC) and Insurance Act, 1938, and may be liable for fines and/or imprisonment.",
    "hasCriminalPenalty": true,
    "hasCivilPenalty": true
  },
  {
    "state": "Karnataka",
    "stateCode": "KA",
    "penaltyDescription": "Knowingly providing false or misleading information to an insurer with intent to defraud is a punishable offence under Indian law and may result in fines, imprisonment, or denial of insurance benefits.",
    "hasCriminalPenalty": true,
    "hasCivilPenalty": true
  },
  {
    "state": "Tamil Nadu",
    "stateCode": "TN",
    "penaltyDescription": "Submitting false or fraudulent insurance claims may lead to criminal prosecution under IPC and civil recovery actions by insurers, including denial of claims and legal damages.",
    "hasCriminalPenalty": true,
    "hasCivilPenalty": true
  },
  {
    "state": "Maharashtra",
    "stateCode": "MH",
    "penaltyDescription": "Insurance fraud, including submission of false or misleading claims, is punishable under applicable provisions of the IPC and regulatory laws, and may result in fines, imprisonment, and blacklisting by insurers.",
    "hasCriminalPenalty": true,
    "hasCivilPenalty": true
  },
  {
    "state": "Andhra Pradesh",
    "stateCode": "AP",
    "penaltyDescription": "Filing false or misleading information in an insurance claim constitutes insurance fraud and may attract criminal prosecution under IPC Sections 420 and related provisions, along with civil liability for damages.",
    "hasCriminalPenalty": true,
    "hasCivilPenalty": true
  }
]
```
### Check if a state has criminal penalties for fraud

**Endpoint:** `GET curl /api/fraud-penalties/MH/has-criminal`

**Request:**
```bash
curl http://localhost:8081/api/fraud-penalties/MH/has-criminal
```

**Response:**
```json
true
```

### Check if a state has civil penalties for fraud

**Endpoint:** `GET curl /api/fraud-penalties/MH/has-civil`

**Request:**
```bash
curl http://localhost:8081/api/fraud-penalties/MH/has-civil
```

**Response:**
```json
true
```

## Testing with Sample Documents

The project includes 5 sample FNOL documents demonstrating different routing scenarios:

### 1. Fast-track Routing
```bash
curl -X POST http://localhost:8081/api/claims/process \
  -F "file=@sample-documents/fnol-fasttrack.txt"
```
**Expected Route:** `Fast-track`  
**Reason:** Low-value claim ($3,500) with all mandatory fields present

### 2. Manual Review (Missing Fields)
```bash
curl -X POST http://localhost:8081/api/claims/process \
  -F "file=@sample-documents/fnol-manual-review.txt"
```
**Expected Route:** `Manual review`  
**Reason:** Missing mandatory fields (effective dates, incident time)

### 3. Investigation Flag
```bash
curl -X POST http://localhost:8081/api/claims/process \
  -F "file=@sample-documents/fnol-investigation.txt"
```
**Expected Route:** `Investigation Flag`  
**Reason:** Description contains fraud keywords ("inconsistent", "staged")

### 4. Specialist Queue
```bash
curl -X POST http://localhost:8081/api/claims/process \
  -F "file=@sample-documents/fnol-specialist.txt"
```
**Expected Route:** `Specialist Queue`  
**Reason:** Claim type is "injury" - requires specialist review

### 5. Manual Review (High Value)
```bash
curl -X POST http://localhost:8081/api/claims/process \
  -F "file=@sample-documents/fnol-high-value.txt"
```
**Expected Route:** `Manual review`  
**Reason:** High-value claim ($45,000 exceeds $25,000 threshold)

## Routing Logic

The system applies routing rules in the following priority order:

1. **Missing Mandatory Fields** → `Manual review` (Highest Priority)
2. **Fraud Keywords Detected** → `Investigation Flag`
3. **Injury Claim Type** → `Specialist Queue`
4. **Low Value + Complete** → `Fast-track` (< $25,000)
5. **High Value** → `Manual review` (≥ $25,000)

## ACORD Form Integration

The application integrates state-specific fraud penalty data from ACORD 2 forms:
- 29 US states covered
- Criminal and civil penalty classifications
- Loaded automatically on application startup from `fraud-penalties.json`
- Accessible via REST API for compliance checks

## Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3.2.2** - Application framework
- **Apache PDFBox 3.0.1** - PDF parsing
- **Lombok** - Code generation
- **Jackson** - JSON serialization
- **Maven** - Build tool

