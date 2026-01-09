package com.helpingHands.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.FundraiserDTO;
import com.helpingHands.demo.services.FundraiserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fundraisers")
@CrossOrigin(origins = "http://localhost:3000")
public class FundraiserController {

    private final FundraiserService fundraiserService;

    // Creating a new fundraiser
    @PostMapping
    public FundraiserDTO createFundraiser(@RequestBody FundraiserDTO fundraiserDTO) {
        return fundraiserService.createFundraiser(fundraiserDTO);
    }

    // Getting all fundraisers
    @GetMapping
    public List<FundraiserDTO> getAllFundraisers() {
        return fundraiserService.getAllFundraisers();
    }

    // Getting a fundraiser by its ID
    @GetMapping("/{fundraiserId}")
    public FundraiserDTO getFundraiserById(@PathVariable String fundraiserId) {
        return fundraiserService.getFundraiserById(fundraiserId);
    }
    
    // Getting all fundraisers except those created by a specific user
    @GetMapping("/view-fundraisers/{userId}")
    public List<FundraiserDTO> getAllFundraisersExceptUserId(@PathVariable String userId) {
        return fundraiserService.getAllFundraisersExceptUserId(userId);
    }
    
    // Getting the latest fundraiser
    @GetMapping("/latest")
    public ResponseEntity<FundraiserDTO> getLatestFundraiser() {
        FundraiserDTO latestFundraiser = fundraiserService.getLatestFundraiser();
        return ResponseEntity.ok(latestFundraiser);
    }

    // Deleting a fundraiser by its ID
    @DeleteMapping("/{fundraiserId}")
    public void deleteFundraiser(@PathVariable String fundraiserId) {
        fundraiserService.deleteFundraiser(fundraiserId);
    }
    
    // Approving or rejecting a fundraiser
    @PatchMapping("/{fundraiserId}/approval")
    public ResponseEntity<String> approveOrRejectFundraiser(
            @PathVariable String fundraiserId,
            @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        try {
            fundraiserService.updateApprovalStatus(fundraiserId, status);
            return ResponseEntity.ok("Fundraiser " + status + " successfully.");
        } catch (RuntimeException e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error updating status: " + e.getStackTrace());
        }
    }
}