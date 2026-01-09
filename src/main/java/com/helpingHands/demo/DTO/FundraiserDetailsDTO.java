package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundraiserDetailsDTO {

	private int id;
    private int fundraiserId; 
    
    private byte[] coverPicture;

    private String videoAppeal;
    private Double remainingAmount;
    private String patientName;
    private Integer patientAge;
    private String patientGender;
    private String medicalCondition;
    private String story;

}