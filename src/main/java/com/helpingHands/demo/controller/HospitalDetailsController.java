package com.helpingHands.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;
import com.helpingHands.demo.services.HospitalDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hospital-details")
@CrossOrigin(origins = "http://13.60.58.158/")
public class HospitalDetailsController {

    private final HospitalDetailsService hospitalDetailsService;

    // Creating hospital details
    @PostMapping
    public ResponseEntity<HospitalDetailsDTO> createHospitalDetails(
            @RequestBody HospitalDetailsDTO hospitalDetailsDTO) {
        HospitalDetailsDTO createdHospitalDetails = hospitalDetailsService.createHospitalDetails(hospitalDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHospitalDetails);
    }

    // Getting hospital details by fundraiser ID
    @GetMapping("/fundraiser/{fundraiserId}")
    public ResponseEntity<HospitalDetailsDTO> getHospitalDetailsByFundraiserId(@PathVariable String fundraiserId) {
        return ResponseEntity.ok(hospitalDetailsService.getHospitalDetailsByFundraiserId(fundraiserId));
    }

    // Getting all hospital details
    @GetMapping
    public ResponseEntity<List<HospitalDetailsDTO>> getAllHospitalDetails() {
        return ResponseEntity.ok(hospitalDetailsService.getAllHospitalDetails());
    }

    // Getting hospital details by ID
    @GetMapping("/{id}")
    public ResponseEntity<HospitalDetailsDTO> getHospitalDetailsById(@PathVariable String id) {
        return ResponseEntity.ok(hospitalDetailsService.getHospitalDetailsById(id));
    }

    // Updating hospital details by ID
    @PutMapping("/{id}")
    public ResponseEntity<HospitalDetailsDTO> updateHospitalDetails(@PathVariable String id,
            @RequestBody HospitalDetailsDTO hospitalDetailsDTO) {
        HospitalDetailsDTO updatedDetails = hospitalDetailsService.updateHospitalDetails(id, hospitalDetailsDTO);
        return ResponseEntity.ok(updatedDetails);
    }

    // Deleting hospital details by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospitalDetails(@PathVariable String id) {
        hospitalDetailsService.deleteHospitalDetails(id);
        return ResponseEntity.noContent().build();
    }
}