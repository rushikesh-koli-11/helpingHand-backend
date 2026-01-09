package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "saved_funds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedFund {
    @Id
    private String saveId;

    @DBRef
    private User user;

    @DBRef
    private Fundraiser fundraiser;
}

