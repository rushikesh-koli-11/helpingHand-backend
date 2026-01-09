package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.BankDetailsDTO;

public interface BankDetailsService {

    List<BankDetailsDTO> getAllBankDetails();
    BankDetailsDTO getBankDetailsByFundraiserId(String fundraiserId);
    BankDetailsDTO getBankDetailsById(String bankId);
    BankDetailsDTO createBankDetails(BankDetailsDTO bankDetailsDTO);
    BankDetailsDTO updateBankDetails(String bankId, BankDetailsDTO bankDetailsDTO);
    void deleteBankDetails(String bankId);
}

