package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.BankDetailsDTO;

public interface BankDetailsService {

    List<BankDetailsDTO> getAllBankDetails();
    BankDetailsDTO getBankDetailsByFundraiserId(int bankId);
    BankDetailsDTO createBankDetails(BankDetailsDTO bankDetailsDTO);
    BankDetailsDTO updateBankDetails(int bankId, BankDetailsDTO bankDetailsDTO);
    void deleteBankDetails(int bankId);
}

