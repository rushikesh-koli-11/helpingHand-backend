package com.helpingHands.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "fundraiserId")
	private Fundraiser fundraiser;
    private BigDecimal amount;
    private String currency;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
