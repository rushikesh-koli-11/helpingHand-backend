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

	private int id;
	private String title;
	private int fundraiserId;
	private Long amount;
	private int userId;
	private String currency;
	private String paymentStatus;
	private String transactionId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
