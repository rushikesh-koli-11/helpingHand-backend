package com.helpingHands.demo.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.helpingHands.demo.DTO.PatientVerificationDTO;
import com.helpingHands.demo.controller.PatientVerificationController;
import com.helpingHands.demo.services.PatientVerificationService;

@ExtendWith(MockitoExtension.class)
public class PatientVerificationControllerTest {

    @Mock
    private PatientVerificationService patientVerificationService;

    @InjectMocks
    private PatientVerificationController patientVerificationController;

    private PatientVerificationDTO patientVerificationDTO;

    @BeforeEach
    void setUp() {
        patientVerificationDTO = PatientVerificationDTO.builder()
                .verificationId(1)
                .fundraiserId(1)
                .adhaarNumber(123456789012L)
                .panNumber("ABCDE1234F")
                .build();
    }

    @Test
    void testCreatePatientVerification() {
        when(patientVerificationService.createPatientVerification(any(PatientVerificationDTO.class)))
                .thenReturn(patientVerificationDTO);

        ResponseEntity<PatientVerificationDTO> response = patientVerificationController.createPatientVerification(patientVerificationDTO);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getVerificationId());
        verify(patientVerificationService, times(1)).createPatientVerification(any(PatientVerificationDTO.class));
    }

    @Test
    void testGetAllPatientVerifications() {
        List<PatientVerificationDTO> patientVerificationList = Arrays.asList(patientVerificationDTO);
        when(patientVerificationService.getAllPatientVerifications()).thenReturn(patientVerificationList);

        ResponseEntity<List<PatientVerificationDTO>> response = patientVerificationController.getAllPatientVerifications();

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(patientVerificationService, times(1)).getAllPatientVerifications();
    }

    @Test
    void testGetPatientVerificationById() {
        when(patientVerificationService.getPatientVerificationByfundraiserId(1)).thenReturn(patientVerificationDTO);

        ResponseEntity<PatientVerificationDTO> response = patientVerificationController.getPatientVerificationById(1);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getFundraiserId());
        verify(patientVerificationService, times(1)).getPatientVerificationByfundraiserId(1);
    }

    @Test
    void testDeletePatientVerification() {
        doNothing().when(patientVerificationService).deletePatientVerification(1);

        assertDoesNotThrow(() -> patientVerificationController.deletePatientVerification(1));
        verify(patientVerificationService, times(1)).deletePatientVerification(1);
    }

    @Test
    void testUpdatePatientVerification() {
        when(patientVerificationService.updatePatientVerification(eq(1), any(PatientVerificationDTO.class)))
                .thenReturn(patientVerificationDTO);

        ResponseEntity<PatientVerificationDTO> response = patientVerificationController.updatePatientVerification(1, patientVerificationDTO);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getVerificationId());
        verify(patientVerificationService, times(1)).updatePatientVerification(eq(1), any(PatientVerificationDTO.class));
    }
}