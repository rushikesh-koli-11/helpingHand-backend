package com.helpingHands.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.exception.ResourceNotFoundException;
import com.helpingHands.demo.globalException.Response;
import com.helpingHands.demo.services.DonationsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationsController {

    private final DonationsService donationService;

    // Saving a new donation
    @PostMapping
    public ResponseEntity<Response<DonationsDTO>> saveDonation(@RequestBody DonationsDTO dto) {
        Response<DonationsDTO> savedDonation = donationService.saveDonation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonation);
    }
    
    // Getting a donation by its ID
    @GetMapping("/{donationId}")
    public ResponseEntity<DonationsDTO> getDonationById(@PathVariable int donationId) {
        DonationsDTO donation = donationService.getDonationById(donationId);
        return ResponseEntity.ok(donation);
    }
    
    // Getting all donations made by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DonationsDTO>> getAllDonationsByUserId(@PathVariable int userId) {
        List<DonationsDTO> donations = donationService.getDonationsByUserId(userId);
        return ResponseEntity.ok(donations);
    }
    
    // Getting all donations made to a specific fundraiser
    @GetMapping("/fundraiser/{fundraiserId}")
    public ResponseEntity<List<DonationsDTO>> getAllDonationsByFundraiserId(@PathVariable int fundraiserId) {
        List<DonationsDTO> donations = donationService.getDonationsByFundraiserId(fundraiserId);
        return ResponseEntity.ok(donations);
    }
    
    // Marking a donation as SUCCESS
    @GetMapping("/success")
    public ResponseEntity<String> markSuccess(@RequestParam int donationId) {
        try {
            donationService.updateDonationStatus(donationId, DonationStatus.SUCCESS);
            return ResponseEntity.ok("Donation marked as SUCCESS");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // Marking a donation as CANCEL
    @GetMapping("/cancel")
    public ResponseEntity<String> markCancel(@RequestParam int donationId) {
        try {
            donationService.updateDonationStatus(donationId, DonationStatus.CANCEL);
            return ResponseEntity.ok("Donation marked as CANCEL");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}