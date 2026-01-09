package com.helpingHands.demo.services;
import java.util.List;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.globalException.Response;
public interface DonationsService {
    Response<DonationsDTO>  saveDonation(DonationsDTO dto);
    DonationsDTO getDonationById(String id);
    List<DonationsDTO> getDonationsByUserId(String userId);
    List<DonationsDTO> getDonationsByFundraiserId(String fundraiserId);
    DonationsDTO getDonationDTO(String donationId);
    public void updateDonationStatus(String donationId, DonationStatus status) ;
}



