package com.helpingHands.demo.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.entities.Donations;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UserRepository;

@Component
public class DonationsMapper {
	
	@Autowired
	private FundraiserRepository fundraiserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Updated formatter for date only

    public Donations toEntity(DonationsDTO dto) {
        Donations donation = new Donations();
        
        Fundraiser fundraiser = fundraiserRepository.findById(dto.getFundraiserId())
                .orElseThrow(() -> new IllegalArgumentException("Fundraiser not found for ID: " + dto.getFundraiserId()));
        
        User user = userRepository.findById(dto.getUserId())
        		.orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + dto.getUserId()));
        
        donation.setFundraiser(fundraiser);
        donation.setUser(user);
        donation.setAmount(dto.getAmount());
        donation.setDonationDate(LocalDate.now());
        donation.setTransactionId(generateTransactionId());
        donation.setStatus(dto.getStatus());
        return donation;
    }

    public DonationsDTO toDTO(Donations donation) {
        if (donation == null) {
            return null;
        }

        DonationsDTO dto = new DonationsDTO();
        dto.setDonationId(donation.getDonationId());
        dto.setFundraiserId(donation.getFundraiser().getId());
        dto.setUserId(donation.getUser().getUserId()); 
        dto.setAmount(donation.getAmount());
        dto.setDonationDate(formatDate(donation.getDonationDate())); 
        dto.setTransactionId(donation.getTransactionId());
        dto.setStatus(donation.getStatus());
        return dto;
    }

    
    private String generateTransactionId() {
        return "T-"+UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String formatDate(LocalDate dateTime) {
        return dateTime.format(DATE_FORMATTER); 
    }
}
