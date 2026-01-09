package com.helpingHands.demo.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.helpingHands.demo.DTO.PaymentDTO;
import com.helpingHands.demo.DTO.PaymentResponseDTO;
import com.helpingHands.demo.controller.PaymentController;
import com.helpingHands.demo.services.PaymentService;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private PaymentDTO paymentDTO;
    private PaymentResponseDTO paymentResponseDTO;

    @BeforeEach
    void setUp() {
        paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(100L);
        paymentDTO.setCurrency("INR");

        paymentResponseDTO = PaymentResponseDTO.builder()
                .status("Success")
                .build();
    }

    @Test
    void testDonateFund() {
        when(paymentService.donateCheckout(paymentDTO)).thenReturn(paymentResponseDTO);

        ResponseEntity<PaymentResponseDTO> response = paymentController.donateFund(paymentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Success", response.getBody().getStatus());
        verify(paymentService, times(1)).donateCheckout(paymentDTO);
    }
}