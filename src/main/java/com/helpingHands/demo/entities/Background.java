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
@Table(name = "Background")
public class Background {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int backgroundId;

    @OneToOne
    @JoinColumn(name = "fundraiserId")
    private Fundraiser fundraiser;

    private String relationWithPatient;
    private Double MonthlyIncomeOfPatientsFamily;


}

