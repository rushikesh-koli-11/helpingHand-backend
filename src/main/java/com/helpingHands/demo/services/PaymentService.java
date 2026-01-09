package com.helpingHands.demo.services;

import com.helpingHands.demo.DTO.PaymentDTO;
import com.helpingHands.demo.DTO.PaymentResponseDTO;

public interface PaymentService {
	public PaymentResponseDTO donateCheckout(PaymentDTO dto);
}
