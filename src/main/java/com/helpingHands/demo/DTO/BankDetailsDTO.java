package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDTO {
	 	private int bankId;
	    private int fundraiserId;  
	    private String bankName;
	    private String accountHolderName;
	    private String accountNumber;
	    private String ifscCode;
	    private String accountType;  
	    private String branchName;
	    private String branchAddress;
}
