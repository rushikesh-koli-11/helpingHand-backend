package com.helpingHands.demo.services;
import java.util.List;

import com.helpingHands.demo.DTO.PatientVerificationDTO;

public interface PatientVerificationService {

    PatientVerificationDTO createPatientVerification(PatientVerificationDTO dto);
    List<PatientVerificationDTO> getAllPatientVerifications();
    PatientVerificationDTO getPatientVerificationByfundraiserId(int id);
    void deletePatientVerification(int id);
    PatientVerificationDTO updatePatientVerification(int id, PatientVerificationDTO dto);
}

