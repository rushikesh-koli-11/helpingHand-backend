package com.helpingHands.demo.services.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpingHands.demo.DTO.BankDetailsDTO;
import com.helpingHands.demo.constants.BankDetailsConstants;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.BankDetails;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.BankDetailsMapper;
import com.helpingHands.demo.repository.BankDetailsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.BankDetailsService;

/*
* Service implementation for Bank Details.
*/
@Service
public class BankDetailsServiceImpl implements BankDetailsService {

	@Autowired
    private BankDetailsRepository bankDetailsRepository;
    
	@Autowired
    private BankDetailsMapper bankDetailsMapper ;
    
	@Autowired
    private FundraiserRepository fundraiserRepository;

    /**
     * Retrieving all bank details.
     * @return List of BankDetailsDTO
     */
    @Override
    public List<BankDetailsDTO> getAllBankDetails() {
        try {
            // Fetching all bank details
            List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
            // Mapping entities to DTOs
            return bankDetailsList.stream()
                    .map(bankDetailsMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomExceptions(BankDetailsConstants.DATABASE_ERROR);
        }
    }

    /**
     * Fetching bank details for a given fundraiser ID.
     * @param fundraiserId ID of the fundraiser
     * @return BankDetailsDTO
     */
    @Override
    public BankDetailsDTO getBankDetailsByFundraiserId(String fundraiserId) {
        BankDetails bankDetails = bankDetailsRepository.findByFundraiserId(fundraiserId)
                .orElse(null);
        
        if (bankDetails == null) {
            System.out.println(BankDetailsConstants.BANK_DETAILS_NOT_FOUND);
            return null;
        }
        
        return bankDetailsMapper.toDTO(bankDetails);
    }

    @Override
    public BankDetailsDTO getBankDetailsById(String bankId) {
        BankDetails bankDetails = bankDetailsRepository.findById(bankId)
                .orElseThrow(() -> new CustomExceptions(BankDetailsConstants.BANK_DETAILS_NOT_FOUND + bankId));
        return bankDetailsMapper.toDTO(bankDetails);
    }

    /**
     * Creating new bank details.
     * @param bankDetailsDTO BankDetailsDTO containing the details
     * @return Created BankDetailsDTO
     */
    @Override
    public BankDetailsDTO createBankDetails(BankDetailsDTO bankDetailsDTO) {
        // Validating bank details input
        if (bankDetailsDTO == null || bankDetailsDTO.getAccountNumber() == null) {
            throw new CustomExceptions(BankDetailsConstants.INVALID_DATA + "Account number is missing.");
        }
        
        // Mapping DTO to entity
        BankDetails bankDetails = bankDetailsMapper.toEntity(bankDetailsDTO);
        try {
            // Saving bank details
            BankDetails savedBankDetails = bankDetailsRepository.save(bankDetails);
            return bankDetailsMapper.toDTO(savedBankDetails);
        } catch (Exception e) {
            throw new CustomExceptions(BankDetailsConstants.DATABASE_ERROR);
        }
    }

    /**
     * Updating existing bank details.
     * @param bankId ID of the bank details
     * @param bankDetailsDTO Updated BankDetailsDTO
     * @return Updated BankDetailsDTO
     */
    @Override
    public BankDetailsDTO updateBankDetails(String bankId, BankDetailsDTO bankDetailsDTO) {
        // Checking if bank details exist
        BankDetails existingBankDetails = bankDetailsRepository.findById(bankId)
                .orElseThrow(() -> new CustomExceptions(BankDetailsConstants.BANK_DETAILS_NOT_FOUND + bankId));

        // Validating input details
        if (bankDetailsDTO == null || bankDetailsDTO.getAccountNumber() == null) {
            throw new CustomExceptions(BankDetailsConstants.INVALID_DATA + "Account number is missing.");
        }

        // Updating bank details
        existingBankDetails.setAccountNumber(bankDetailsDTO.getAccountNumber());
        existingBankDetails.setIfscCode(bankDetailsDTO.getIfscCode());
        existingBankDetails.setAccountHolderName(bankDetailsDTO.getAccountHolderName());
        existingBankDetails.setBankName(bankDetailsDTO.getBankName());
        
        try {
            // Saving updated details
            BankDetails updatedBankDetails = bankDetailsRepository.save(existingBankDetails);
            return bankDetailsMapper.toDTO(updatedBankDetails);
        } catch (Exception e) {
            throw new CustomExceptions(BankDetailsConstants.DATABASE_ERROR);
        }
    }

    /**
     * Deleting bank details by ID.
     * @param bankId ID of the bank details to delete
     */
    @Override
    public void deleteBankDetails(String bankId) {
        // Checking if bank details exist
        if (!bankDetailsRepository.existsById(bankId)) {
            throw new CustomExceptions(BankDetailsConstants.BANK_DETAILS_NOT_FOUND + bankId);
        }
        try {
            // Deleting bank details
            bankDetailsRepository.deleteById(bankId);
        } catch (Exception e) {
            throw new CustomExceptions(BankDetailsConstants.DATABASE_ERROR);
        }
    }
}
