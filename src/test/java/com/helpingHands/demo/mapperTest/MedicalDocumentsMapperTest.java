package com.helpingHands.demo.mapperTest;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.MedicalDocuments;
import com.helpingHands.demo.mapper.MedicalDocumentsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class MedicalDocumentsMapperTest {

    private MedicalDocumentsMapper medicalDocumentsMapper;

    @BeforeEach
    void setUp() {
        medicalDocumentsMapper = new MedicalDocumentsMapper();
    }

    @Test
    void testToDTO() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        byte[] medicalEstimate = "Estimate".getBytes();
        byte[] consentLetter = "Consent Letter".getBytes();
        byte[] medicalReports = "Reports".getBytes();
        byte[] otherDocs = "Other Docs".getBytes();

        MedicalDocuments medicalDocuments = new MedicalDocuments();
        medicalDocuments.setMedicalDocumentId(1);
        medicalDocuments.setFundraiser(fundraiser);
        medicalDocuments.setMedicalEstimate(medicalEstimate);
        medicalDocuments.setConsentLetterFromPatient(consentLetter);
        medicalDocuments.setMedicalReports(medicalReports);
        medicalDocuments.setOtherDocs(otherDocs);
        medicalDocuments.setAdditionalInformation("Additional Info");

        MedicalDocumentsDTO dto = medicalDocumentsMapper.toDTO(medicalDocuments);

        assertNotNull(dto);
        assertEquals(1, dto.getMedicalDocumentId());
        assertEquals(1, dto.getFundraiserId());
        assertTrue(Arrays.equals(medicalEstimate, dto.getMedicalEstimate()));
        assertTrue(Arrays.equals(consentLetter, dto.getConsentLetterFromPatient()));
        assertTrue(Arrays.equals(medicalReports, dto.getMedicalReports()));
        assertTrue(Arrays.equals(otherDocs, dto.getOtherDocs()));
        assertEquals("Additional Info", dto.getAdditionalInformation());
    }

    @Test
    void testToDTO_NullEntity() {
        assertNull(medicalDocumentsMapper.toDTO(null));
    }

    @Test
    void testToEntity() {
        byte[] medicalEstimate = "Estimate".getBytes();
        byte[] consentLetter = "Consent Letter".getBytes();
        byte[] medicalReports = "Reports".getBytes();
        byte[] otherDocs = "Other Docs".getBytes();

        MedicalDocumentsDTO dto = MedicalDocumentsDTO.builder()
                .medicalDocumentId(1)
                .fundraiserId(1)
                .medicalEstimate(medicalEstimate)
                .consentLetterFromPatient(consentLetter)
                .medicalReports(medicalReports)
                .otherDocs(otherDocs)
                .additionalInformation("Additional Info")
                .build();

        MedicalDocuments entity = medicalDocumentsMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1, entity.getMedicalDocumentId());
        assertEquals(1, entity.getFundraiser().getId());
        assertTrue(Arrays.equals(medicalEstimate, entity.getMedicalEstimate()));
        assertTrue(Arrays.equals(consentLetter, entity.getConsentLetterFromPatient()));
        assertTrue(Arrays.equals(medicalReports, entity.getMedicalReports()));
        assertTrue(Arrays.equals(otherDocs, entity.getOtherDocs()));
        assertEquals("Additional Info", entity.getAdditionalInformation());
    }

    @Test
    void testToEntity_NullDTO() {
        assertNull(medicalDocumentsMapper.toEntity(null));
    }
}
