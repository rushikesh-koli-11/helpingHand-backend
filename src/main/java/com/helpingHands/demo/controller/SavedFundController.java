package com.helpingHands.demo.controller;

import java.util.List;

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

import com.helpingHands.demo.DTO.SavedFundDTO;
import com.helpingHands.demo.services.SavedFundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saved-fundraisers")
public class SavedFundController {

    private final SavedFundService service;

    // Saving a fundraiser
    @PostMapping
    public ResponseEntity<SavedFundDTO> saveFundraiser(@RequestBody SavedFundDTO dto) {
        return ResponseEntity.ok(service.saveFund(dto));
    }

    // Updating a saved fundraiser
    @PutMapping("/{saveId}")
    public ResponseEntity<SavedFundDTO> updateFundraiser(@PathVariable String saveId, @RequestBody SavedFundDTO dto) {
        return ResponseEntity.ok(service.updateSavedFund(saveId, dto));
    }

    // Getting a saved fundraiser by its ID
    @GetMapping("/{saveId}")
    public ResponseEntity<SavedFundDTO> getFundraiser(@PathVariable String saveId) {
        return ResponseEntity.ok(service.getSavedFundById(saveId));
    }

    // Getting all saved fundraisers
    @GetMapping
    public ResponseEntity<List<SavedFundDTO>> getAllFundraisers() {
        return ResponseEntity.ok(service.getAllSavedFunds());
    }

    // Deleting a saved fundraiser by its ID
    @DeleteMapping("/{saveId}")
    public ResponseEntity<Void> deleteFundraiser(@PathVariable String saveId) {
        service.deleteSavedFund(saveId);
        return ResponseEntity.noContent().build();
    }
}