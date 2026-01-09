package com.helpingHands.demo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fundraiser {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double goalAmount;

    @Column(nullable = false)
    private Double currentAmount;

    
    
    @Column(nullable = false)
    private Long mobileNumber;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    
    @Column
    private String status; 


    @JsonCreator
    public Fundraiser(@JsonProperty("id") int id) {
        this.id = id;
    }
    
    @OneToOne(mappedBy = "fundraiserId")
    PatientVerification patientVerification;
    
    @OneToOne(mappedBy = "fundraiser")
    FundraiserDetails fundraiserDetails;
    
    @OneToOne(mappedBy = "fundraiser")
    HospitalDetails hospitalDetails;
    
    @OneToOne(mappedBy = "fundraiser")
    BankDetails bankDetails;
    
    @OneToOne(mappedBy = "fundraiser")
    Background background;
    
    @OneToMany(mappedBy = "fundraiser")
    List<Updates> updates;
    
}
