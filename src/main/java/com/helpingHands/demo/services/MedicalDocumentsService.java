package com.helpingHands.demo.services;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;

public interface MedicalDocumentsService {
	MedicalDocumentsDTO uploadDocuments(MedicalDocumentsDTO dto);

	Optional<MedicalDocumentsDTO> getMedicalDocumentsByFundraiserId(String fundraiserId);

	MedicalDocumentsDTO updateDocuments(String fundraiserId, MultipartFile medicalEstimate,
			MultipartFile consentLetterFromPatient, MultipartFile medicalReports, MultipartFile otherDocs,
			String additionalInformation);
}
