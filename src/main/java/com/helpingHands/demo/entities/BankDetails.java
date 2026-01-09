package com.helpingHands.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int bankId;

	    @OneToOne
	    @JoinColumn(name = "fundraiserId")
	    private Fundraiser fundraiser;

	    private String bankName;
	    private String accountHolderName;
	    private String accountNumber;
	    private String ifscCode;
	    private String accountType; 
	    private String branchName;
	    private String branchAddress;
}
