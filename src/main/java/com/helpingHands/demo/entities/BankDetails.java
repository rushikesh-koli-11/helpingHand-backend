package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "bank_details")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    @Id
    private String bankId;

    @DBRef
    private Fundraiser fundraiser;

    private String bankName;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    private String accountType; 
    private String branchName;
    private String branchAddress;
}
