package com.helpingHands.demo.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "donations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donations {
    @Id
    private String donationId;

    @DBRef
    private User user;

    @DBRef
    private Fundraiser fundraiser;

    private Double amount;
    private LocalDate donationDate;
    private String transactionId;
    private DonationStatus status;
}



