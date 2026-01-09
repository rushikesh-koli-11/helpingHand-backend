package com.helpingHands.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.BankDetailsDTO;
import com.helpingHands.demo.entities.BankDetails;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.repository.FundraiserRepository;

@Component
public class BankDetailsMapper {

    @Autowired
    private FundraiserRepository fundraiserRepository; 


    public BankDetails toEntity(BankDetailsDTO bankDetailsDTO) {
    	if (bankDetailsDTO == null) {
            return null;
        }

        BankDetails bankDetails = new BankDetails();
        Fundraiser fundraiser = fundraiserRepository.findById(bankDetailsDTO.getFundraiserId())
                .orElseThrow(() -> new IllegalArgumentException("Fundraiser not found for ID: " + bankDetailsDTO.getFundraiserId()));

        bankDetails.setFundraiser(fundraiser);
        bankDetails.setBankName(bankDetailsDTO.getBankName());
        bankDetails.setAccountHolderName(bankDetailsDTO.getAccountHolderName());
        bankDetails.setAccountNumber(bankDetailsDTO.getAccountNumber());
        bankDetails.setIfscCode(bankDetailsDTO.getIfscCode());
        bankDetails.setAccountType(bankDetailsDTO.getAccountType());
        bankDetails.setBranchName(bankDetailsDTO.getBranchName());
        bankDetails.setBranchAddress(bankDetailsDTO.getBranchAddress());

        return bankDetails;
    }


    public BankDetailsDTO toDTO(BankDetails bankDetails) {
    	if (bankDetails == null) {
            return null;
        }
        BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();

        bankDetailsDTO.setBankId(bankDetails.getBankId());
        bankDetailsDTO.setBankName(bankDetails.getBankName());
        bankDetailsDTO.setAccountHolderName(bankDetails.getAccountHolderName());
        bankDetailsDTO.setAccountNumber(bankDetails.getAccountNumber());
        bankDetailsDTO.setIfscCode(bankDetails.getIfscCode());
        bankDetailsDTO.setAccountType(bankDetails.getAccountType());
        bankDetailsDTO.setBranchName(bankDetails.getBranchName());
        bankDetailsDTO.setBranchAddress(bankDetails.getBranchAddress());
        if (bankDetails.getFundraiser() != null) {
            bankDetailsDTO.setFundraiserId(bankDetails.getFundraiser().getId());
        }

        return bankDetailsDTO;
    }
}
