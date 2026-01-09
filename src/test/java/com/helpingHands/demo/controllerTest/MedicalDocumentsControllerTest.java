package com.helpingHands.demo.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.controller.MedicalDocumentsController;
import com.helpingHands.demo.services.MedicalDocumentsService;

@ExtendWith(MockitoExtension.class)
public class MedicalDocumentsControllerTest {

    @Mock
    private MedicalDocumentsService medicalDocumentsService;

    @InjectMocks
    private MedicalDocumentsController medicalDocumentsController;

    private MedicalDocumentsDTO medicalDocumentsDTO;

    @BeforeEach
    void setUp() {
        medicalDocumentsDTO = MedicalDocumentsDTO.builder()
                .medicalDocumentId(1)
                .fundraiserId(1)
                .medicalEstimate(new byte[]{1, 2, 3})
                .consentLetterFromPatient(new byte[]{4, 5, 6})
                .medicalReports(new byte[]{7, 8, 9})
                .otherDocs(new byte[]{10, 11, 12})
                .additionalInformation("Test Info")
                .build();
    }

    @Test
    void testUploadMedicalDocuments() {
        MockMultipartFile medicalEstimate = new MockMultipartFile("medicalEstimate", "estimate.pdf", "application/pdf", new byte[]{1, 2, 3});
        MockMultipartFile consentLetter = new MockMultipartFile("consentLetterFromPatient", "consent.pdf", "application/pdf", new byte[]{4, 5, 6});
        MockMultipartFile medicalReports = new MockMultipartFile("medicalReports", "report.pdf", "application/pdf", new byte[]{7, 8, 9});
        MockMultipartFile otherDocs = new MockMultipartFile("otherDocs", "other.pdf", "application/pdf", new byte[]{10, 11, 12});
        
        when(medicalDocumentsService.uploadDocuments(any(MedicalDocumentsDTO.class))).thenReturn(medicalDocumentsDTO);

        ResponseEntity<MedicalDocumentsDTO> response = medicalDocumentsController.uploadMedicalDocuments(
                medicalEstimate, consentLetter, medicalReports, otherDocs, "Test Info", 1);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getFundraiserId());
        verify(medicalDocumentsService, times(1)).uploadDocuments(any(MedicalDocumentsDTO.class));
    }

    @Test
    void testGetMedicalDocuments_Found() {
        when(medicalDocumentsService.getMedicalDocumentsByFundraiserId(1)).thenReturn(Optional.of(medicalDocumentsDTO));

        ResponseEntity<MedicalDocumentsDTO> response = medicalDocumentsController.getMedicalDocuments(1);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getFundraiserId());
        verify(medicalDocumentsService, times(1)).getMedicalDocumentsByFundraiserId(1);
    }

    @Test
    void testGetMedicalDocuments_NotFound() {
        when(medicalDocumentsService.getMedicalDocumentsByFundraiserId(9)).thenReturn(Optional.empty());

        ResponseEntity<MedicalDocumentsDTO> response = medicalDocumentsController.getMedicalDocuments(9);

        assertEquals(404, response.getStatusCode().value());
        verify(medicalDocumentsService, times(1)).getMedicalDocumentsByFundraiserId(9);
    }

    @Test
    void testUpdateMedicalDocuments() {
        MockMultipartFile medicalEstimate = new MockMultipartFile("medicalEstimate", "estimate.pdf", "application/pdf", new byte[]{1, 2, 3});
        MockMultipartFile consentLetter = new MockMultipartFile("consentLetterFromPatient", "consent.pdf", "application/pdf", new byte[]{4, 5, 6});
        MockMultipartFile medicalReports = new MockMultipartFile("medicalReports", "report.pdf", "application/pdf", new byte[]{7, 8, 9});
        MockMultipartFile otherDocs = new MockMultipartFile("otherDocs", "other.pdf", "application/pdf", new byte[]{10, 11, 12});
        
        when(medicalDocumentsService.updateDocuments(eq(1), any(), any(), any(), any(), anyString()))
                .thenReturn(medicalDocumentsDTO);

        ResponseEntity<MedicalDocumentsDTO> response = medicalDocumentsController.updateMedicalDocuments(
                1, medicalEstimate, consentLetter, medicalReports, otherDocs, "Updated Info");

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getFundraiserId());
        verify(medicalDocumentsService, times(1)).updateDocuments(eq(1), any(), any(), any(), any(), anyString());
    }
}