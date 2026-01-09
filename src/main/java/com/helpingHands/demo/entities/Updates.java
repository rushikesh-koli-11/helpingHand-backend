package com.helpingHands.demo.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Updates")
public class Updates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int updateId;

    @ManyToOne
    @JoinColumn(name = "fundraiserId")
    private Fundraiser fundraiser;

    private String content;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

}

