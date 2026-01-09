package com.helpingHands.demo.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.controller.PDFReceiptController;
import com.helpingHands.demo.services.DonationsService;
import com.helpingHands.demo.services.PDFService;
import com.helpingHands.demo.services.UserServices;

@ExtendWith(MockitoExtension.class)
public class PDFReceiptControllerTest {

    @InjectMocks
    private PDFReceiptController pdfReceiptController;

    @Mock
    private PDFService pdfService;

    @Mock
    private UserServices userServices;

    @Mock
    private DonationsService donationsService;

    private DonationsDTO donation;
    private UserDTO user;
    @SuppressWarnings("unused")
	private String receiptTemplate;

    @BeforeEach
    void setUp() throws Exception {
        donation = new DonationsDTO();
        donation.setUserId(1);
        donation.setAmount(100.0);
        donation.setDonationDate("2024-03-04");
        donation.setTransactionId("TXN123456");
        
        user = new UserDTO();
        user.setUserId(1);
        user.setName("Varun Kulkarni");
        user.setEmail("varun@galaxe.com");
        user.setContactNumber("1234567890");
        
        Path htmlPath = new ClassPathResource("templates/receipt.html").getFile().toPath();
        receiptTemplate = Files.readString(htmlPath);
    }

    @Test
    void testGenerateReceipt() throws Exception {
        when(donationsService.getDonationById(anyInt())).thenReturn(donation);
        when(userServices.getUserById(anyInt())).thenReturn(user);
        when(pdfService.generateReceipt(anyString())).thenReturn(new byte[]{1, 2, 3});
        
        ResponseEntity<byte[]> response = pdfReceiptController.generateReceipt(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());
        assertTrue(response.getHeaders().getContentDisposition().toString().contains(user.getName()));

        verify(donationsService, times(1)).getDonationById(1);
        verify(userServices, times(1)).getUserById(1);
        verify(pdfService, times(1)).generateReceipt(anyString());
    }
}