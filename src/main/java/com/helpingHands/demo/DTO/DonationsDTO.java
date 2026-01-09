package com.helpingHands.demo.DTO;

import com.helpingHands.demo.entities.DonationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationsDTO {
	
	private int donationId;
    private int userId;
    private int fundraiserId;
    private Double amount;
    private String donationDate;
    private String transactionId;
    
    @Builder.Default
    private DonationStatus status = DonationStatus.SUCCESS;
}
