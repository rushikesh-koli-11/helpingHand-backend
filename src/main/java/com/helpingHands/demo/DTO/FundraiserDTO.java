package com.helpingHands.demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundraiserDTO {
	private String fundraiserId;
	private String userId;
    private String title;
    private String description;
    private Double goalAmount;
    private Double currentAmount;
    
    @Builder.Default
    private String status = "pending";
    
    private Long mobileNumber;
    FundraiserDetailsDTO fundraiserDetailsDTO;
}

