package com.helpingHands.demo.serviceImplTest;


import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.constants.MedicalDocumentsConstants;
import com.helpingHands.demo.entities.MedicalDocuments;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.MedicalDocumentsMapper;
import com.helpingHands.demo.repository.MedicalDocumentsRepository;
import com.helpingHands.demo.services.serviceImpl.MedicalDocumentsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
public class MedicalDocumentsServiceImplTest {

    @InjectMocks
    private MedicalDocumentsServiceImpl medicalDocumentsService;

    @Mock
    private MedicalDocumentsRepository medicalDocumentsRepository;

    @Mock
    private MedicalDocumentsMapper medicalDocumentsMapper;

    @Mock
    private MultipartFile mockFile;

    private MedicalDocuments medicalDocuments;
    private MedicalDocumentsDTO medicalDocumentsDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalDocuments = new MedicalDocuments();
        medicalDocumentsDTO = new MedicalDocumentsDTO();
    }

    @Test
    void testUploadDocuments_Success() {
        // Arrange
        byte[] fileBytes = new byte[]{1, 2, 3};
        medicalDocumentsDTO.setMedicalEstimate(fileBytes);

        when(medicalDocumentsMapper.toEntity(any())).thenReturn(medicalDocuments);
        when(medicalDocumentsRepository.save(any())).thenReturn(medicalDocuments);
        when(medicalDocumentsMapper.toDTO(any())).thenReturn(medicalDocumentsDTO);

        // Act
        MedicalDocumentsDTO result = medicalDocumentsService.uploadDocuments(medicalDocumentsDTO);

        // Assert
        assertNotNull(result);
        assertArrayEquals(fileBytes, result.getMedicalEstimate());
    }

    @Test
    void testGetMedicalDocumentsByFundraiserId_Success() {
        int fundraiserId = 1;
        when(medicalDocumentsRepository.findByFundraiser_Id(fundraiserId)).thenReturn(Optional.of(medicalDocuments));
        when(medicalDocumentsMapper.toDTO(any())).thenReturn(medicalDocumentsDTO);

        Optional<MedicalDocumentsDTO> result = medicalDocumentsService.getMedicalDocumentsByFundraiserId(fundraiserId);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetMedicalDocumentsByFundraiserId_NotFound() {
        int fundraiserId = 1;
        when(medicalDocumentsRepository.findByFundraiser_Id(fundraiserId)).thenReturn(Optional.empty());

        Optional<MedicalDocumentsDTO> result = medicalDocumentsService.getMedicalDocumentsByFundraiserId(fundraiserId);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateDocuments_Success() throws Exception {
        int fundraiserId = 1;
        byte[] fileBytes = new byte[]{1, 2, 3};

        when(mockFile.getBytes()).thenReturn(fileBytes);
        when(medicalDocumentsRepository.findByFundraiser_Id(fundraiserId)).thenReturn(Optional.of(medicalDocuments));
        when(medicalDocumentsRepository.save(any())).thenReturn(medicalDocuments);
        when(medicalDocumentsMapper.toDTO(any())).thenReturn(medicalDocumentsDTO);

        MedicalDocumentsDTO result = medicalDocumentsService.updateDocuments(
                fundraiserId, mockFile, mockFile, mockFile, mockFile, "Updated info");

        assertNotNull(result);
    }

    @Test
    void testUpdateDocuments_NotFound() {
        int fundraiserId = 1;
        when(medicalDocumentsRepository.findByFundraiser_Id(fundraiserId)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () ->
                medicalDocumentsService.updateDocuments(fundraiserId, mockFile, mockFile, mockFile, mockFile, "Updated info"));

        assertEquals(MedicalDocumentsConstants.MEDICAL_DOCUMENTS_NOT_FOUND + fundraiserId, exception.getMessage());
    }
}
