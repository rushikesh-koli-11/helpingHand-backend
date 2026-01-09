package com.helpingHands.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "PatientVerification")
public class PatientVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int verificationId;

    @OneToOne
    @JoinColumn(name = "fundraiserId")
    private Fundraiser fundraiserId;//fundraiser object

    private Long adhaarNumber; 
    private String panNumber;

}

