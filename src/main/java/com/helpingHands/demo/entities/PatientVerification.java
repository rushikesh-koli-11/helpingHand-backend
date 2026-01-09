package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "patient_verifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientVerification {
    @Id
    private String verificationId;

    @DBRef
    private Fundraiser fundraiser;

    private Long adhaarNumber; 
    private String panNumber;
}

