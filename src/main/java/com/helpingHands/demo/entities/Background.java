package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "backgrounds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Background {
    @Id
    private String backgroundId;

    @DBRef
    private Fundraiser fundraiser;

    private String relationWithPatient;
    private Double MonthlyIncomeOfPatientsFamily;
}

