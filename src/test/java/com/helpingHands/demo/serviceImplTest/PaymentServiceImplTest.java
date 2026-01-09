package com.helpingHands.demo.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.helpingHands.demo.DTO.PaymentDTO;
import com.helpingHands.demo.DTO.PaymentResponseDTO;
import com.helpingHands.demo.constants.PaymentConstants;
import com.helpingHands.demo.entities.Donations;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.DonationsMapper;
import com.helpingHands.demo.repository.DonationsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.serviceImpl.PaymentServiceImpl;
import com.stripe.model.checkout.Session;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private DonationsRepository donationsRepository;
    
    @Mock
    private FundraiserRepository fundraiserRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private DonationsMapper donationsMapper;
    
    @Mock
    private Session mockSession;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private PaymentDTO validPaymentDTO;
    private Fundraiser fundraiser;
    private User user;
    private Donations donations;
    
    @BeforeEach
    void setUp() {
        validPaymentDTO = new PaymentDTO();
        validPaymentDTO.setAmount((long) 100);
        validPaymentDTO.setCurrency("USD");
        validPaymentDTO.setTitle("Medical Fundraiser");
        validPaymentDTO.setFundraiserId(1);
        validPaymentDTO.setUserId(1);

        fundraiser = new Fundraiser();
        fundraiser.setId(1);

        user = new User();
        user.setUserId(1);

        donations = new Donations();
        donations.setDonationId(1);
        donations.setAmount(100.0);
        donations.setDonationDate(LocalDate.now());
    }

    @Test
    void testDonateCheckout_Success() throws Exception {
        
        when(fundraiserRepository.findById(validPaymentDTO.getFundraiserId())).thenReturn(Optional.of(fundraiser));
        when(userRepository.findById(validPaymentDTO.getUserId())).thenReturn(Optional.of(user));
        when(donationsMapper.toEntity(any())).thenReturn(donations);
        when(donationsRepository.save(any(Donations.class))).thenReturn(donations);
        when(mockSession.getId()).thenReturn("session_123");
        when(mockSession.getUrl()).thenReturn("https://checkout.stripe.com/session_123");

        
        PaymentResponseDTO response = paymentService.donateCheckout(validPaymentDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals("session_123", response.getSessionId());
        assertEquals("https://checkout.stripe.com/session_123", response.getSessionUrl());
    }

    @Test
    void testDonateCheckout_InvalidAmount_ThrowsException() {
        
        validPaymentDTO.setAmount((long) 0);

         
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            paymentService.donateCheckout(validPaymentDTO);
        });

        assertEquals(PaymentConstants.INVALID_DONATION_AMOUNT, exception.getMessage());
    }

    @Test
    void testDonateCheckout_FundraiserNotFound_ThrowsException() {
        
        when(fundraiserRepository.findById(validPaymentDTO.getFundraiserId())).thenReturn(Optional.empty());

         
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            paymentService.donateCheckout(validPaymentDTO);
        });

        assertTrue(exception.getMessage().contains("Fundraiser not found"));
    }

    @Test
    void testDonateCheckout_UserNotFound_ThrowsException() {
        
        when(fundraiserRepository.findById(validPaymentDTO.getFundraiserId())).thenReturn(Optional.of(fundraiser));
        when(userRepository.findById(validPaymentDTO.getUserId())).thenReturn(Optional.empty());

         
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            paymentService.donateCheckout(validPaymentDTO);
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testDonateCheckout_StripeSessionCreationFailure_ThrowsException() {
        
        when(fundraiserRepository.findById(validPaymentDTO.getFundraiserId())).thenReturn(Optional.of(fundraiser));
        when(userRepository.findById(validPaymentDTO.getUserId())).thenReturn(Optional.of(user));
        when(donationsMapper.toEntity(any())).thenReturn(donations);
        when(donationsRepository.save(any(Donations.class))).thenReturn(donations);

        // Simulate an exception when creating a Stripe session
        when(mockSession.getId()).thenThrow(new RuntimeException("Stripe API error"));

         
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            paymentService.donateCheckout(validPaymentDTO);
        });

        assertEquals(PaymentConstants.PAYMENT_SESSION_CREATION_ERROR, exception.getMessage());
    }
}
