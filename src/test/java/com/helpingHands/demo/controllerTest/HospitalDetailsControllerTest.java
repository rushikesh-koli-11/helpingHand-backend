package com.helpingHands.demo.controllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;
import com.helpingHands.demo.controller.HospitalDetailsController;
import com.helpingHands.demo.services.HospitalDetailsService;

@ExtendWith(MockitoExtension.class)
public class HospitalDetailsControllerTest {

    @Mock
    private HospitalDetailsService hospitalDetailsService;

    @InjectMocks
    private HospitalDetailsController hospitalDetailsController;

    private HospitalDetailsDTO hospitalDetailsDTO;

    @BeforeEach
    void setUp() {
        hospitalDetailsDTO = HospitalDetailsDTO.builder()
                .id(1)
                .fundraiserId(1)
                .hospitalName("City Hospital")
                .hospitalAddress("123 Main Street, Banglore")
                .patientUHIDNumber(98765432101234L)
                .consultingDoctor("Dr. Chandu")
                .doctorPhoneNumber(1234567890L)
                .additionalInformation("Critical condition")
                .build();
    }

    @Test
    void testCreateHospitalDetails() {
        when(hospitalDetailsService.createHospitalDetails(any(HospitalDetailsDTO.class))).thenReturn(hospitalDetailsDTO);
        ResponseEntity<HospitalDetailsDTO> response = hospitalDetailsController.createHospitalDetails(hospitalDetailsDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(hospitalDetailsDTO, response.getBody());
    }

    @Test
    void testGetHospitalDetailsByFundraiserId() {
        when(hospitalDetailsService.getHospitalDetailsByFundraiserId(1)).thenReturn(hospitalDetailsDTO);
        ResponseEntity<HospitalDetailsDTO> response = hospitalDetailsController.getHospitalDetailsByFundraiserId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospitalDetailsDTO, response.getBody());
    }

    @Test
    void testGetAllHospitalDetails() {
        List<HospitalDetailsDTO> hospitalList = Arrays.asList(hospitalDetailsDTO);
        when(hospitalDetailsService.getAllHospitalDetails()).thenReturn(hospitalList);
        ResponseEntity<List<HospitalDetailsDTO>> response = hospitalDetailsController.getAllHospitalDetails();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testUpdateHospitalDetails() {
        when(hospitalDetailsService.updateHospitalDetails(eq(1), any(HospitalDetailsDTO.class))).thenReturn(hospitalDetailsDTO);
        ResponseEntity<HospitalDetailsDTO> response = hospitalDetailsController.updateHospitalDetails(1, hospitalDetailsDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospitalDetailsDTO, response.getBody());
    }

    @Test
    void testDeleteHospitalDetails() {
        doNothing().when(hospitalDetailsService).deleteHospitalDetails(1);
        ResponseEntity<Void> response = hospitalDetailsController.deleteHospitalDetails(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}