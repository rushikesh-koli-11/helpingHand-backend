package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.FundraiserDTO;

public interface FundraiserService {

	FundraiserDTO createFundraiser(FundraiserDTO fundraiserDTO);
    List<FundraiserDTO> getAllFundraisers();
    FundraiserDTO getFundraiserById(String fundraiserId);
    void deleteFundraiser(String fundraiserId);
    FundraiserDTO getLatestFundraiser();
    void updateApprovalStatus(String fundraiserId, String status);
    public List<FundraiserDTO> getAllFundraisersExceptUserId(String userId);
}
