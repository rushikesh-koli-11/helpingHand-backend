//package com.helpingHands.demo.services;
//
//import java.time.LocalDate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.helpingHands.demo.DTO.DonationsDTO;
//import com.helpingHands.demo.DTO.PaymentDTO;
//import com.helpingHands.demo.DTO.PaymentResponseDTO;
//import com.helpingHands.demo.entities.Donations;
//import com.helpingHands.demo.entities.Fundraiser;
//import com.helpingHands.demo.entities.User;
//import com.helpingHands.demo.mapper.DonationsMapper;
//import com.helpingHands.demo.repository.DonationsRepository;
//import com.helpingHands.demo.repository.FundraiserDetailsRepository;
//import com.helpingHands.demo.repository.FundraiserRepository;
//import com.helpingHands.demo.repository.UserRepository;
//import com.stripe.Stripe;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
//import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
//@Service
//public class StripService  {
//
//	@Autowired
//	private DonationsRepository donationsRepository;//donations repo
//	@Autowired
//	private FundraiserRepository fundraiserRepository;
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private DonationsMapper donationsMapper;
//	
//    public PaymentResponseDTO donateCheckout(PaymentDTO dto) {
//    	
//        Stripe.apiKey = "your api key";
//
//        Session session = null;
//
//        try {
//            ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                    .setName(dto.getTitle())
//                    .build();
//
//            PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
//                    .setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "INR")
//                    .setUnitAmount(dto.getAmount() * 100) 
//                    .setProductData(productData)
//                    .build();
//            System.out.println(priceData.getUnitAmount());
//            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
//                    .setQuantity(1L)
//                    .setPriceData(priceData)
//                    .build();
//            
//            //creating  donations entry
//            Fundraiser fundraiser = fundraiserRepository.findById(dto.getFundraiserId()).get();
//            User user = userRepository.findById(dto.getUserId()).get();
//            DonationsDTO
//            donationsDTO = DonationsDTO.builder()
//            .amount(Double.valueOf(dto.getAmount()))
//            .donationDate(LocalDate.now().toString())
//            .status(null)
//            .fundraiserId(dto.getFundraiserId())
//            .userId(dto.getUserId())
//            .build();
//            Donations donations = donationsMapper.toEntity(donationsDTO);
//            donations.setFundraiser(fundraiser);
//            donations.setUser(user);
//            Donations savedDonation = donationsRepository.save(donations);
//            
//           
//            
//            
//            
//            
//            SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
//                    .setMode(SessionCreateParams.Mode.PAYMENT)
//                    .setSuccessUrl("http://13.60.58.158/api/donations/success/"+savedDonation.getDonationId())
//                    .setCancelUrl("http://13.60.58.158/api/donations/cancel/"+savedDonation.getDonationId())
//                    .addLineItem(lineItem) 
//                    .build();
//            // Create Session
//            session = Session.create(sessionCreateParams);
//
//            
//        } catch (Exception e) {
//            System.err.println("Error creating Stripe session: " + e.getMessage());
//            e.printStackTrace();
//            return PaymentResponseDTO.builder()
//                    .status("Failure")
//                    .build();
//        }
//        
//        // Return the response if session is created
//        return PaymentResponseDTO.builder()
//                .status("Success")
//                .sessionId(session.getId())
//                .sessionUrl(session.getUrl())
//                .build();
//    }
//}