package com.helpingHands.demo.serviceImplTest;

import com.helpingHands.demo.DTO.PatientVerificationDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.PatientVerificationConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.PatientVerification;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.PatientVerificationMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.PatientVerificationRepository;
import com.helpingHands.demo.services.serviceImpl.PatientVerificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientVerificationServiceImplTest {

    @InjectMocks
    private PatientVerificationServiceImpl patientVerificationService;

    @Mock
    private PatientVerificationRepository patientVerificationRepository;

    @Mock
    private FundraiserRepository fundraiserRepository;

    @Mock
    private PatientVerificationMapper patientVerificationMapper;

    private PatientVerificationDTO patientVerificationDTO;
    private PatientVerification patientVerification;
    private Fundraiser fundraiser;

    @BeforeEach
    void setUp() {
        fundraiser = new Fundraiser();
        fundraiser.setId(1);

        patientVerificationDTO = new PatientVerificationDTO();
        patientVerificationDTO.setFundraiserId(1);
        patientVerificationDTO.setAdhaarNumber(123456789012L);
        patientVerificationDTO.setPanNumber("ABCDE1234F");

        patientVerification = new PatientVerification();
        patientVerification.setVerificationId(1);
        patientVerification.setAdhaarNumber(123456789012L);
        patientVerification.setPanNumber("ABCDE1234F");
        patientVerification.setFundraiserId(fundraiser);
    }

    @Test
    void testCreatePatientVerification_Success() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(patientVerificationMapper.toEntity(any(), any())).thenReturn(patientVerification);
        when(patientVerificationRepository.save(any())).thenReturn(patientVerification);
        when(patientVerificationMapper.toDTO(any())).thenReturn(patientVerificationDTO);

        PatientVerificationDTO result = patientVerificationService.createPatientVerification(patientVerificationDTO);

        assertNotNull(result);
        assertEquals(patientVerificationDTO.getAdhaarNumber(), result.getAdhaarNumber());
        verify(patientVerificationRepository, times(1)).save(any());
    }

    @Test
    void testCreatePatientVerification_FundraiserNotFound_ThrowsException() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () ->
                patientVerificationService.createPatientVerification(patientVerificationDTO));

        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    void testGetAllPatientVerifications_Success() {
        when(patientVerificationRepository.findAll()).thenReturn(Collections.singletonList(patientVerification));
        when(patientVerificationMapper.toDTOList(any())).thenReturn(Collections.singletonList(patientVerificationDTO));

        List<PatientVerificationDTO> result = patientVerificationService.getAllPatientVerifications();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetPatientVerificationByFundraiserId_Success() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(patientVerificationMapper.toDTO(any())).thenReturn(patientVerificationDTO);

        PatientVerificationDTO result = patientVerificationService.getPatientVerificationByfundraiserId(1);

        assertNotNull(result);
        assertEquals(123456789012L, result.getAdhaarNumber());
    }

    @Test
    void testGetPatientVerificationByFundraiserId_FundraiserNotFound_ThrowsException() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () ->
                patientVerificationService.getPatientVerificationByfundraiserId(1));

        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    void testUpdatePatientVerification_Success() {
        when(patientVerificationRepository.findById(1)).thenReturn(Optional.of(patientVerification));
        when(patientVerificationRepository.save(any())).thenReturn(patientVerification);
        when(patientVerificationMapper.toDTO(any())).thenReturn(patientVerificationDTO);

        PatientVerificationDTO result = patientVerificationService.updatePatientVerification(1, patientVerificationDTO);

        assertNotNull(result);
        assertEquals(123456789012L, result.getAdhaarNumber());
    }

    @Test
    void testUpdatePatientVerification_NotFound_ThrowsException() {
        when(patientVerificationRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () ->
                patientVerificationService.updatePatientVerification(1, patientVerificationDTO));

        assertEquals(PatientVerificationConstants.PATIENT_VERIFICATION_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    void testDeletePatientVerification_Success() {
        doNothing().when(patientVerificationRepository).deleteById(1);

        assertDoesNotThrow(() -> patientVerificationService.deletePatientVerification(1));
        verify(patientVerificationRepository, times(1)).deleteById(1);
    }
}