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
    private String medicalDocumentId;
    private String fundraiserId;
    private String medicalEstimate; // Cloudinary URL
    private String consentLetterFromPatient; // Cloudinary URL
    private String medicalReports; // Cloudinary URL
    private String otherDocs; // Cloudinary URL
    private String additionalInformation;
}


