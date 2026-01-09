package com.helpingHands.demo.mapper;


import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.MedicalDocuments;

@Component
public class MedicalDocumentsMapper {

	public MedicalDocumentsDTO toDTO(MedicalDocuments medicalDocuments) {
	    if (medicalDocuments == null) {
	        return null;
	    }

	    return MedicalDocumentsDTO.builder()
	            .medicalDocumentId(medicalDocuments.getMedicalDocumentId())
	            .fundraiserId(medicalDocuments.getFundraiser() != null ? medicalDocuments.getFundraiser().getId() : null)
	            .medicalEstimate(medicalDocuments.getMedicalEstimate())
	            .consentLetterFromPatient(medicalDocuments.getConsentLetterFromPatient())
	            .medicalReports(medicalDocuments.getMedicalReports())
	            .otherDocs(medicalDocuments.getOtherDocs())
	            .additionalInformation(medicalDocuments.getAdditionalInformation())
	            .build();
	}


	public MedicalDocuments toEntity(MedicalDocumentsDTO dto) {
	    if (dto == null) {
	        return null;
	    }

	    MedicalDocuments medicalDocuments = new MedicalDocuments();
	    medicalDocuments.setMedicalDocumentId(dto.getMedicalDocumentId());

	    // Set fundraiser, assuming you have the fundraiser ID and can fetch the Fundraiser object
	    Fundraiser fundraiser = new Fundraiser();
	    fundraiser.setId(dto.getFundraiserId());
	    medicalDocuments.setFundraiser(fundraiser);

	    medicalDocuments.setMedicalEstimate(dto.getMedicalEstimate());
	    medicalDocuments.setConsentLetterFromPatient(dto.getConsentLetterFromPatient());
	    medicalDocuments.setMedicalReports(dto.getMedicalReports());
	    medicalDocuments.setOtherDocs(dto.getOtherDocs());
	    medicalDocuments.setAdditionalInformation(dto.getAdditionalInformation());

	    return medicalDocuments;
	}

}
