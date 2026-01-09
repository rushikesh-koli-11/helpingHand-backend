package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "medical_documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDocuments {
    @Id
    private String medicalDocumentId;

    @DBRef
    private Fundraiser fundraiser;

    private String medicalEstimate; // Cloudinary URL
    private String consentLetterFromPatient; // Cloudinary URL
    private String medicalReports; // Cloudinary URL
    private String otherDocs; // Cloudinary URL
    private String additionalInformation;
}
