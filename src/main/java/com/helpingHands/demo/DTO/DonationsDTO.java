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
	private String donationId;
    private String userId;
    private String fundraiserId;
    private Double amount;
    private String donationDate;
    private String transactionId;
    
    @Builder.Default
    private DonationStatus status = DonationStatus.SUCCESS;
}
