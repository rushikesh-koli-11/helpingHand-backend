package com.helpingHands.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.PatientVerificationDTO;
import com.helpingHands.demo.services.PatientVerificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient-verifications")
public class PatientVerificationController {

    private final PatientVerificationService patientVerificationService;

    // Creating a new patient verification
    @PostMapping
    public ResponseEntity<PatientVerificationDTO> createPatientVerification(@RequestBody PatientVerificationDTO dto) {
        return ResponseEntity.ok(patientVerificationService.createPatientVerification(dto));
    }

    // Getting all patient verifications
    @GetMapping
    public ResponseEntity<List<PatientVerificationDTO>> getAllPatientVerifications() {
        List<PatientVerificationDTO> patientVerificationDTOs = patientVerificationService.getAllPatientVerifications();
        return ResponseEntity.ok(patientVerificationDTOs);
    }

    // Getting a patient verification by fundraiser ID
    @GetMapping("/fundraiser/{fundraiserId}")
    public ResponseEntity<PatientVerificationDTO> getPatientVerificationByFundraiserId(@PathVariable String fundraiserId) {
        return ResponseEntity.ok(patientVerificationService.getPatientVerificationByfundraiserId(fundraiserId));
    }

    // Deleting a patient verification by ID
    @DeleteMapping("/{id}")
    public void deletePatientVerification(@PathVariable String id) {
        patientVerificationService.deletePatientVerification(id);
    }

    // Updating a patient verification by ID
    @PutMapping("/{id}")
    public ResponseEntity<PatientVerificationDTO> updatePatientVerification(
            @PathVariable String id, 
            @RequestBody PatientVerificationDTO dto) {
        PatientVerificationDTO updatedDto = patientVerificationService.updatePatientVerification(id, dto);
        return ResponseEntity.ok(updatedDto);
    }
}