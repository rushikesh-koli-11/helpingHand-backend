package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "fundraisers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fundraiser {

    @Id
    private String id;

    private String title;
    private String description;
    private Double goalAmount;
    private Double currentAmount;
    private Long mobileNumber;
    
    @DBRef
    private User user;
    
    private String status;
}
