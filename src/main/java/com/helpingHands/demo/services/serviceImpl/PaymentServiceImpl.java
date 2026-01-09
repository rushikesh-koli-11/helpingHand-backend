package com.helpingHands.demo.services.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.DTO.PaymentDTO;
import com.helpingHands.demo.DTO.PaymentResponseDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.PaymentConstants;
import com.helpingHands.demo.constants.UserConstants;
import com.helpingHands.demo.entities.Donations;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.DonationsMapper;
import com.helpingHands.demo.repository.DonationsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;

/**
 * Implementation of {@link PaymentService}.
 * This service handles donation payments through Stripe's checkout session.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private DonationsRepository donationsRepository;
    
    @Autowired
    private FundraiserRepository fundraiserRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DonationsMapper donationsMapper;
    
    /**
     * Initiates a Stripe checkout session for processing a donation payment.
     *
     * @param dto The {@link PaymentDTO} containing donation details.
     * @return {@link PaymentResponseDTO} containing Stripe session details.
     * @throws CustomExceptions If the donation amount is invalid, the fundraiser or user is not found, 
     *                          or if there is an error during session creation.
     */
    @Override
    public PaymentResponseDTO donateCheckout(PaymentDTO dto) {
        Stripe.apiKey = PaymentConstants.STRIPE_API_KEY;

        Session session = null;

        try {
            // Validate the donation data
            if (dto == null || dto.getAmount() <= 0) {
                throw new CustomExceptions(PaymentConstants.INVALID_DONATION_AMOUNT);
            }

            // Set up Stripe session
            ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(dto.getTitle())
                    .build();

            PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency(dto.getCurrency() != null ? dto.getCurrency() : PaymentConstants.DEFAULT_CURRENCY)
                    .setUnitAmount(dto.getAmount() * 100)
                    .setProductData(productData)
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(priceData)
                    .build();

            // Find fundraiser and user
            Fundraiser fundraiser = fundraiserRepository.findById(dto.getFundraiserId())
                    .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + dto.getFundraiserId()));
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND + dto.getUserId()));

            // Create the donation entry
            DonationsDTO donationsDTO = DonationsDTO.builder()
                    .amount(Double.valueOf(dto.getAmount()))
                    .donationDate(LocalDate.now().toString())
                    .status(null)
                    .fundraiserId(dto.getFundraiserId())
                    .userId(dto.getUserId())
                    .build();
            Donations donations = donationsMapper.toEntity(donationsDTO);
            donations.setFundraiser(fundraiser);
            donations.setUser(user);
            
            // Save donation in repository
            Donations savedDonation = donationsRepository.save(donations);

            // Create Stripe checkout session
            SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(PaymentConstants.SUCCESS_URL + savedDonation.getDonationId())
                    .setCancelUrl(PaymentConstants.CANCEL_URL + savedDonation.getDonationId())
                    .addLineItem(lineItem)
                    .build();

            session = Session.create(sessionCreateParams);

        } catch (CustomExceptions e) {
            throw e;  // Rethrow custom exceptions to be handled by global exception handler
        } catch (Exception e) {
            throw new CustomExceptions(PaymentConstants.PAYMENT_SESSION_CREATION_ERROR);
        }

        // Return the response with session URL if session is created successfully
        return PaymentResponseDTO.builder()
                .status("Success")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
