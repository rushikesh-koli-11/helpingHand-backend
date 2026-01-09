package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientVerificationDTO {
	private String verificationId;
    private String fundraiserId;
    private Long adhaarNumber; 
    private String panNumber;
}
