package com.helpingHands.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.PaymentDTO;
import com.helpingHands.demo.DTO.PaymentResponseDTO;
import com.helpingHands.demo.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    // Processing a donation payment checkout
    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDTO> donateFund(@RequestBody PaymentDTO dto) {
        PaymentResponseDTO paymentResponseDTO = paymentService.donateCheckout(dto);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDTO);
    }
}