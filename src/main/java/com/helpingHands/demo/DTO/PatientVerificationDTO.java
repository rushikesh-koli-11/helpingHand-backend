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

	private int verificationId;
    private int fundraiserId;
    private Long adhaarNumber; 
    private String panNumber;
}
