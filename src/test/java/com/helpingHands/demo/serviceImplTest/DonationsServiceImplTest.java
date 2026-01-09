package com.helpingHands.demo.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.entities.Donations;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.DonationsMapper;
import com.helpingHands.demo.repository.DonationsRepository;
import com.helpingHands.demo.repository.FundraiserDetailsRepository;
import com.helpingHands.demo.services.PDFService;
import com.helpingHands.demo.services.serviceImpl.DonationsServiceImpl;
import com.helpingHands.demo.services.serviceImpl.EmailService;

@ExtendWith(MockitoExtension.class)
public class DonationsServiceImplTest {
    
    @Mock
    private DonationsRepository donationRepository;
    
    @Mock
    private FundraiserDetailsRepository fundraiserDetailsRepository;
    
    @Mock
    private DonationsMapper donationMapper;
    
    @Mock
    private EmailService emailService;
    
    @Mock
    private PDFService pdfService;
    
    @InjectMocks
    private DonationsServiceImpl donationsService;
    
    private Donations donation;
    private DonationsDTO donationDTO;
    private FundraiserDetails fundraiserDetails;
    private Fundraiser fundraiser;
    private User user;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        user = new User();
        user.setUserId(1);
        user.setName("Om Parshetti");
        user.setEmail("om@galaxe.com");
        user.setContactNumber("1234567890");
        
        fundraiserDetails = new FundraiserDetails();
        fundraiserDetails.setId(1);
        fundraiserDetails.setRemainingAmount(10000.0);
        
        fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setTitle("Medical Help");
        fundraiser.setFundraiserDetails(fundraiserDetails);
        
        donation = new Donations();
        donation.setDonationId(1);
        donation.setUser(user);
        donation.setFundraiser(fundraiser);
        donation.setAmount(1000.0);
        donation.setTransactionId(UUID.randomUUID().toString());
        donation.setDonationDate(LocalDate.now());
        donation.setStatus(DonationStatus.PENDING);
        
        donationDTO = new DonationsDTO(1, 1, 1, 1000.0, donation.getDonationDate().toString(), donation.getTransactionId(), DonationStatus.PENDING);
    }
    
    @Test
    void testSaveDonation() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.of(fundraiserDetails));
        when(donationMapper.toEntity(any(DonationsDTO.class))).thenReturn(donation);
        when(donationRepository.save(any(Donations.class))).thenReturn(donation);
        when(donationMapper.toDTO(any(Donations.class))).thenReturn(donationDTO);
        
        assertDoesNotThrow(() -> donationsService.saveDonation(donationDTO));
    }
    
    @Test
    void testGetDonationById() {
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        when(donationMapper.toDTO(donation)).thenReturn(donationDTO);
        
        DonationsDTO result = donationsService.getDonationById(1);
        assertNotNull(result);
        assertEquals(1000.0, result.getAmount());
    }
    
    /// failing --------------------------------------------------------
    @Test
    void testUpdateDonationStatus_Success() throws Exception {
        // Arrange
        FundraiserDetails fundraiserDetails = new FundraiserDetails();
        fundraiserDetails.setRemainingAmount(10000.0);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setFundraiserDetails(fundraiserDetails);
        
        donation.setFundraiser(fundraiser);

        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        when(donationRepository.save(any(Donations.class))).thenReturn(donation);
        when(fundraiserDetailsRepository.findById(anyInt())).thenReturn(Optional.of(fundraiserDetails));
        when(fundraiserDetailsRepository.save(any(FundraiserDetails.class))).thenReturn(fundraiserDetails);
        when(pdfService.generateReceipt(anyString())).thenReturn(new byte[10]);

        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString(), any(), anyString());

        assertDoesNotThrow(() -> donationsService.updateDonationStatus(1, DonationStatus.SUCCESS));
        assertEquals(DonationStatus.SUCCESS, donation.getStatus());
    }


    
    @Test
    void testGetDonationsByUserId() {
        when(donationRepository.findAll()).thenReturn(Arrays.asList(donation));
        
        assertFalse(donationsService.getDonationsByUserId(1).isEmpty());
    }
    
    @Test
    void testGetDonationsByFundraiserId() {
        when(donationRepository.findAll()).thenReturn(Arrays.asList(donation));
        
        assertFalse(donationsService.getDonationsByFundraiserId(1).isEmpty());
    }

}
