package com.helpingHands.demo.services;
import java.util.List;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.globalException.Response;
public interface DonationsService {
    Response<DonationsDTO>  saveDonation(DonationsDTO dto);
    DonationsDTO getDonationById(int id);
    List<DonationsDTO> getDonationsByUserId(int userId);
    List<DonationsDTO> getDonationsByFundraiserId(int fundraiserId);
    DonationsDTO getDonationDTO(int donationId);
    public void updateDonationStatus(int donationId, DonationStatus status) ;
}



