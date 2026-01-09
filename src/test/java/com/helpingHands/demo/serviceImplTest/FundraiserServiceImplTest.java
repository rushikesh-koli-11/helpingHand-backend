package com.helpingHands.demo.serviceImplTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.helpingHands.demo.DTO.FundraiserDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.FundraiserMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.serviceImpl.EmailService;
import com.helpingHands.demo.services.serviceImpl.FundraiserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FundraiserServiceImplTest {

    @Mock
    private FundraiserRepository fundraiserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FundraiserMapper fundraiserMapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private FundraiserServiceImpl fundraiserService;

    private Fundraiser fundraiser;
    private FundraiserDTO fundraiserDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setName("Test User");

        fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setUser(user);
        fundraiser.setStatus("pending");

        fundraiserDTO = new FundraiserDTO();
        fundraiserDTO.setFundraiserId(1);
        fundraiserDTO.setUserId(1);
    }

    @Test
    void createFundraiser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(fundraiserMapper.toEntity(fundraiserDTO, user)).thenReturn(fundraiser);
        when(fundraiserRepository.save(fundraiser)).thenReturn(fundraiser);
        when(fundraiserMapper.toDTO(fundraiser)).thenReturn(fundraiserDTO);

        FundraiserDTO result = fundraiserService.createFundraiser(fundraiserDTO);

        assertNotNull(result);
        assertEquals(1, result.getFundraiserId());
        verify(fundraiserRepository, times(1)).save(fundraiser);
    }

    @Test
    void createFundraiser_UserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            fundraiserService.createFundraiser(fundraiserDTO);
        });
        
        assertEquals(FundraiserConstants.USER_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void getAllFundraisers_Success() {
        List<Fundraiser> fundraisers = Arrays.asList(fundraiser);
        when(fundraiserRepository.findAll()).thenReturn(fundraisers);
        when(fundraiserMapper.toDTO(any(Fundraiser.class))).thenReturn(fundraiserDTO);

        List<FundraiserDTO> result = fundraiserService.getAllFundraisers();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getFundraiserById_Found() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(fundraiserMapper.toDTO(fundraiser)).thenReturn(fundraiserDTO);

        FundraiserDTO result = fundraiserService.getFundraiserById(1);
        assertNotNull(result);
        assertEquals(1, result.getFundraiserId());
    }

    @Test
    void getFundraiserById_NotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            fundraiserService.getFundraiserById(1);
        });
        
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void deleteFundraiser_Success() {
        when(fundraiserRepository.existsById(1)).thenReturn(true);
        doNothing().when(fundraiserRepository).deleteById(1);

        assertDoesNotThrow(() -> fundraiserService.deleteFundraiser(1));
        verify(fundraiserRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteFundraiser_NotFound() {
        when(fundraiserRepository.existsById(1)).thenReturn(false);

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            fundraiserService.deleteFundraiser(1);
        });
        
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void updateApprovalStatus_Approved() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));

        fundraiserService.updateApprovalStatus(1, "approved");

        assertEquals("approved", fundraiser.getStatus());
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), contains("Fundraiser Approved"), anyString());
        verify(fundraiserRepository, times(1)).save(fundraiser);
    }

    @Test
    void updateApprovalStatus_NotApproved() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));

        fundraiserService.updateApprovalStatus(1, "not approved");

        assertEquals("not approved", fundraiser.getStatus());
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), contains("Fundraiser Not Approved"), anyString());
        verify(fundraiserRepository, times(1)).save(fundraiser);
    }

    @Test
    void updateApprovalStatus_FundraiserNotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            fundraiserService.updateApprovalStatus(1, "approved");
        });
        
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());
    }
}

