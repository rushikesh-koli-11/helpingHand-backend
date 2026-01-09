package com.helpingHands.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.services.DonationsService;
import com.helpingHands.demo.services.PDFService;
import com.helpingHands.demo.services.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class PDFReceiptController {

    private final PDFService pdfService;
    private final UserServices userServices;
    private final DonationsService donationsService;

    // Generating a PDF receipt for a donation
    @GetMapping("/generate/{donationId}")
    public ResponseEntity<byte[]> generateReceipt(@PathVariable String donationId) throws Exception {
        // Fetching donation details by donation ID
        DonationsDTO donation = donationsService.getDonationById(donationId);

        // Fetching user details by user ID from the donation
        UserDTO user = userServices.getUserById(donation.getUserId());

        // Loading the HTML template for the receipt
        Path htmlPath = ResourceUtils.getFile("classpath:templates/receipt.html").toPath();
        String receiptContent = Files.readString(htmlPath);

        // Replacing placeholders in the HTML template with actual data
        receiptContent = receiptContent.replace("{{name}}", user.getName());
        receiptContent = receiptContent.replace("{{email}}", user.getEmail());
        receiptContent = receiptContent.replace("{{contactNumber}}", user.getContactNumber());
        receiptContent = receiptContent.replace("{{amount}}", donation.getAmount().toString());
        receiptContent = receiptContent.replace("{{donationDate}}", donation.getDonationDate());
        receiptContent = receiptContent.replace("{{transactionId}}", donation.getTransactionId());
        receiptContent = receiptContent.replace("{{receiptNumber}}", "R-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // Generating the PDF from the updated HTML content
        byte[] pdfBytes = pdfService.generateReceipt(receiptContent);

        // Setting up HTTP headers for the PDF response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "%s".formatted(user.getName()).concat(".pdf"));

        // Returning the PDF as a response
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}