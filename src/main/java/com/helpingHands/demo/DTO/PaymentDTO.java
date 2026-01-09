package com.helpingHands.demo.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
	private String id;
	private String title;
	private String fundraiserId;
	private Long amount;
	private String userId;
	private String currency;
	private String paymentStatus;
	private String transactionId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
