package com.helpingHands.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.BankDetailsDTO;
import com.helpingHands.demo.services.BankDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-details")
@CrossOrigin(origins = "http://localhost:3000")
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    // Getting all bank details
    @GetMapping
    public List<BankDetailsDTO> getAllBankDetails() {
        return bankDetailsService.getAllBankDetails();
    }

    // Getting bank details by fundraiser ID
    @GetMapping("/{fundraiserId}")
    public BankDetailsDTO getBankDetailsById(@PathVariable int fundraiserId) {
        return bankDetailsService.getBankDetailsByFundraiserId(fundraiserId);
    }

    // Adding new bank details
    @PostMapping
    public BankDetailsDTO createBankDetails(@RequestBody BankDetailsDTO bankDetailsDTO) {
        return bankDetailsService.createBankDetails(bankDetailsDTO);
    }

    // Updating existing bank details by bank ID
    @PutMapping("/{bankId}")
    public BankDetailsDTO updateBankDetails(@PathVariable int bankId, @RequestBody BankDetailsDTO bankDetailsDTO) {
        return bankDetailsService.updateBankDetails(bankId, bankDetailsDTO);
    }

    // Deleting bank details by bank ID
    @DeleteMapping("/{bankId}")
    public void deleteBankDetails(@PathVariable int bankId) {
        bankDetailsService.deleteBankDetails(bankId);
    }
}