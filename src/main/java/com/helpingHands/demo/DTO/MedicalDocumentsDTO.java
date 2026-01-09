package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MedicalDocumentsDTO {
    private int medicalDocumentId;
    private int fundraiserId;
    private byte[] medicalEstimate;
    private byte[] consentLetterFromPatient;
    private byte[] medicalReports;
    private byte[] otherDocs;
    private String additionalInformation;
}


