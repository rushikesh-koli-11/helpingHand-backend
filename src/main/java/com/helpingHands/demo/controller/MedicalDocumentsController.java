package com.helpingHands.demo.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.services.CloudinaryService;
import com.helpingHands.demo.services.MedicalDocumentsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical-documents")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicalDocumentsController {

    private final MedicalDocumentsService medicalDocumentsService;
    private final CloudinaryService cloudinaryService;

    // Uploading medical documents for a fundraiser
    @PostMapping("/upload")
    public ResponseEntity<MedicalDocumentsDTO> uploadMedicalDocuments(
        @RequestParam(required = false) MultipartFile medicalEstimate,
        @RequestParam(value = "consentLetterFromPatient", required = false) MultipartFile consentLetterFromPatient,
        @RequestParam(value = "medicalReports", required = false) MultipartFile medicalReports,
        @RequestParam(value = "otherDocs", required = false) MultipartFile otherDocs,
        @RequestParam("additionalInformation") String additionalInformation,
        @RequestParam("fundraiserId") String fundraiserId
    ) throws IOException {
        // Creating a DTO object and uploading files to Cloudinary
        MedicalDocumentsDTO dto = new MedicalDocumentsDTO();
        dto.setFundraiserId(fundraiserId);

        if (medicalEstimate != null && !medicalEstimate.isEmpty()) {
            String url = cloudinaryService.uploadFile(medicalEstimate, "medical-documents");
            dto.setMedicalEstimate(url);
        }
        if (consentLetterFromPatient != null && !consentLetterFromPatient.isEmpty()) {
            String url = cloudinaryService.uploadFile(consentLetterFromPatient, "medical-documents");
            dto.setConsentLetterFromPatient(url);
        }
        if (medicalReports != null && !medicalReports.isEmpty()) {
            String url = cloudinaryService.uploadFile(medicalReports, "medical-documents");
            dto.setMedicalReports(url);
        }
        if (otherDocs != null && !otherDocs.isEmpty()) {
            String url = cloudinaryService.uploadFile(otherDocs, "medical-documents");
            dto.setOtherDocs(url);
        }
        dto.setAdditionalInformation(additionalInformation);

        // Uploading the documents and returning the saved DTO
        MedicalDocumentsDTO savedDTO = medicalDocumentsService.uploadDocuments(dto);
        return ResponseEntity.ok(savedDTO);
    }

    // Fetching medical documents by fundraiser ID
    @GetMapping("/fetch/{fundraiserId}")
    public ResponseEntity<MedicalDocumentsDTO> getMedicalDocuments(@PathVariable String fundraiserId) {
        Optional<MedicalDocumentsDTO> dtoOptional = medicalDocumentsService.getMedicalDocumentsByFundraiserId(fundraiserId);
        return dtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Updating medical documents for a fundraiser
    @PutMapping("/update/{fundraiserId}")
    public ResponseEntity<MedicalDocumentsDTO> updateMedicalDocuments(
        @PathVariable String fundraiserId,
        @RequestParam(required = false) MultipartFile medicalEstimate,
        @RequestParam(required = false) MultipartFile consentLetterFromPatient,
        @RequestParam(required = false) MultipartFile medicalReports,
        @RequestParam(required = false) MultipartFile otherDocs,
        @RequestParam String additionalInformation
    ) {
        MedicalDocumentsDTO updatedDTO = medicalDocumentsService.updateDocuments(
            fundraiserId, medicalEstimate, consentLetterFromPatient, medicalReports, otherDocs, additionalInformation);
        return ResponseEntity.ok(updatedDTO);
    }
}