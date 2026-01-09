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
import com.helpingHands.demo.services.MedicalDocumentsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical-documents")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicalDocumentsController {

    private final MedicalDocumentsService medicalDocumentsService;

    // Uploading medical documents for a fundraiser
    @PostMapping("/upload")
    public ResponseEntity<MedicalDocumentsDTO> uploadMedicalDocuments(
        @RequestParam MultipartFile medicalEstimate,
        @RequestParam("consentLetterFromPatient") MultipartFile consentLetterFromPatient,
        @RequestParam("medicalReports") MultipartFile medicalReports,
        @RequestParam("otherDocs") MultipartFile otherDocs,
        @RequestParam("additionalInformation") String additionalInformation,
        @RequestParam("fundraiserId") int fundraiserId
    ) {
        // Creating a DTO object and mapping the uploaded files and fields
        MedicalDocumentsDTO dto = new MedicalDocumentsDTO();
        dto.setFundraiserId(fundraiserId);

        if (medicalEstimate != null) {
            dto.setMedicalEstimate(convertFileToByteArray(medicalEstimate));
        }
        if (consentLetterFromPatient != null) {
            dto.setConsentLetterFromPatient(convertFileToByteArray(consentLetterFromPatient));
        }
        if (medicalReports != null) {
            dto.setMedicalReports(convertFileToByteArray(medicalReports));
        }
        if (otherDocs != null) {
            dto.setOtherDocs(convertFileToByteArray(otherDocs));
        }
        dto.setAdditionalInformation(additionalInformation);

        // Uploading the documents and returning the saved DTO
        MedicalDocumentsDTO savedDTO = medicalDocumentsService.uploadDocuments(dto);
        return ResponseEntity.ok(savedDTO);
    }

    // Fetching medical documents by fundraiser ID
    @GetMapping("/fetch/{fundraiserId}")
    public ResponseEntity<MedicalDocumentsDTO> getMedicalDocuments(@PathVariable int fundraiserId) {
        Optional<MedicalDocumentsDTO> dtoOptional = medicalDocumentsService.getMedicalDocumentsByFundraiserId(fundraiserId);
        return dtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Updating medical documents for a fundraiser
    @PutMapping("/update/{fundraiserId}")
    public ResponseEntity<MedicalDocumentsDTO> updateMedicalDocuments(
        @PathVariable int fundraiserId,
        @RequestParam MultipartFile medicalEstimate,
        @RequestParam MultipartFile consentLetterFromPatient,
        @RequestParam MultipartFile medicalReports,
        @RequestParam MultipartFile otherDocs,
        @RequestParam String additionalInformation
    ) {
        MedicalDocumentsDTO updatedDTO = medicalDocumentsService.updateDocuments(
            fundraiserId, medicalEstimate, consentLetterFromPatient, medicalReports, otherDocs, additionalInformation);
        return ResponseEntity.ok(updatedDTO);
    }

    // Helper method to convert a MultipartFile to a byte array
    private byte[] convertFileToByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error converting file to byte array", e);
        }
    }
}