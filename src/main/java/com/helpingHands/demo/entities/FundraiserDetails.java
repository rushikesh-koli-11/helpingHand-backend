package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "fundraiser_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundraiserDetails {

    @Id
    private String id;

    @DBRef
    private Fundraiser fundraiser; 

    private String coverPicture; // Cloudinary URL
    
    private String videoAppeal;
    private Double remainingAmount;
    private String patientName;
    private Integer patientAge;
    private String patientGender;
    private String medicalCondition;
    private String story;
}
