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
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    // Getting all bank details
    @GetMapping
    public List<BankDetailsDTO> getAllBankDetails() {
        return bankDetailsService.getAllBankDetails();
    }

    // Getting bank details by fundraiser ID
    @GetMapping("/fundraiser/{fundraiserId}")
    public BankDetailsDTO getBankDetailsByFundraiserId(@PathVariable String fundraiserId) {
        return bankDetailsService.getBankDetailsByFundraiserId(fundraiserId);
    }

    // Getting bank details by ID
    @GetMapping("/{bankId}")
    public BankDetailsDTO getBankDetailsById(@PathVariable String bankId) {
        return bankDetailsService.getBankDetailsById(bankId);
    }

    // Adding new bank details
    @PostMapping
    public BankDetailsDTO createBankDetails(@RequestBody BankDetailsDTO bankDetailsDTO) {
        return bankDetailsService.createBankDetails(bankDetailsDTO);
    }

    // Updating existing bank details by bank ID
    @PutMapping("/{bankId}")
    public BankDetailsDTO updateBankDetails(@PathVariable String bankId, @RequestBody BankDetailsDTO bankDetailsDTO) {
        return bankDetailsService.updateBankDetails(bankId, bankDetailsDTO);
    }

    // Deleting bank details by bank ID
    @DeleteMapping("/{bankId}")
    public void deleteBankDetails(@PathVariable String bankId) {
        bankDetailsService.deleteBankDetails(bankId);
    }
}