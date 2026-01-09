package com.helpingHands.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fundraiser_details")
public class FundraiserDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "fundraiser_id")
    private Fundraiser fundraiser; 

    @Lob
    private byte[] coverPicture;
    
    private String videoAppeal;
    private Double remainingAmount;
    private String patientName;
    private Integer patientAge;
    private String patientGender;
    private String medicalCondition;
    private String story;

   
}
