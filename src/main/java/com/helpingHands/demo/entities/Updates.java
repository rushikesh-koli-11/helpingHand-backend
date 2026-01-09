package com.helpingHands.demo.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "updates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Updates {
    @Id
    private String updateId;

    @DBRef
    private Fundraiser fundraiser;

    private String content;
    
    @CreatedDate
    private LocalDateTime createdAt;
}

