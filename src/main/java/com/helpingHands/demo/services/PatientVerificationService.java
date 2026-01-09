package com.helpingHands.demo.services;
import java.util.List;

import com.helpingHands.demo.DTO.PatientVerificationDTO;

public interface PatientVerificationService {

    PatientVerificationDTO createPatientVerification(PatientVerificationDTO dto);
    List<PatientVerificationDTO> getAllPatientVerifications();
    PatientVerificationDTO getPatientVerificationByfundraiserId(String id);
    void deletePatientVerification(String id);
    PatientVerificationDTO updatePatientVerification(String id, PatientVerificationDTO dto);
}

