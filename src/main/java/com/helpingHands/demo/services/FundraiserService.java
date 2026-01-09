package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.FundraiserDTO;

public interface FundraiserService {

	FundraiserDTO createFundraiser(FundraiserDTO fundraiserDTO);
    List<FundraiserDTO> getAllFundraisers();
    FundraiserDTO getFundraiserById(int fundraiserId);
    void deleteFundraiser(int fundraiserId);
    FundraiserDTO getLatestFundraiser();
    void updateApprovalStatus(int fundraiserId, String status);
    public List<FundraiserDTO> getAllFundraisersExceptUserId(Integer userId);
}
