package com.helpingHands.demo.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.FundraiserDetailsConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.FundraiserDetailsMapper;
import com.helpingHands.demo.repository.FundraiserDetailsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.serviceImpl.FundraiserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FundraiserDetailsServiceImplTest {

    @Mock
    private FundraiserDetailsRepository fundraiserDetailsRepository;

    @Mock
    private FundraiserRepository fundraiserRepository;

    @Mock
    private FundraiserDetailsMapper fundraiserDetailsMapper;

    @Mock
    private MultipartFile coverPicture;

    @InjectMocks
    private FundraiserDetailsServiceImpl fundraiserDetailsService;

    private Fundraiser fundraiser;
    private FundraiserDetails fundraiserDetails;
    private FundraiserDetailsDTO fundraiserDetailsDTO;

    @BeforeEach
    void setUp() {
        fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setGoalAmount(10000.0);
        fundraiser.setCurrentAmount(2000.0);

        fundraiserDetails = new FundraiserDetails();
        fundraiserDetails.setId(1);
        fundraiserDetails.setFundraiser(fundraiser);

        fundraiserDetailsDTO = new FundraiserDetailsDTO();
        fundraiserDetailsDTO.setFundraiserId(1);
    }

    @Test
    void testCreateFundraiserDetails_Success() throws IOException {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(coverPicture.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(fundraiserDetailsMapper.toEntity(any(FundraiserDetailsDTO.class), eq(fundraiser))).thenReturn(fundraiserDetails);
        when(fundraiserDetailsRepository.save(any(FundraiserDetails.class))).thenReturn(fundraiserDetails);

        assertNotNull(fundraiserDetailsService.createFundraiserDetails(coverPicture, 1, "video", "Om", 22, "Male", "Condition", "Story"));
    }

    @Test
    void testCreateFundraiserDetails_FundraiserNotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
            fundraiserDetailsService.createFundraiserDetails(coverPicture, 1, "video", "Om", 22, "Male", "Condition", "Story")
        );

        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testGetFundraiserDetailsById_Success() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.of(fundraiserDetails));
        when(fundraiserDetailsMapper.toDTO(fundraiserDetails)).thenReturn(fundraiserDetailsDTO);

        assertNotNull(fundraiserDetailsService.getFundraiserDetailsById(1));
    }

    @Test
    void testGetFundraiserDetailsById_NotFound() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
            fundraiserDetailsService.getFundraiserDetailsById(1)
        );

        assertEquals(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testDeleteFundraiserDetails_Success() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.of(fundraiserDetails));
        doNothing().when(fundraiserDetailsRepository).delete(fundraiserDetails);

        assertDoesNotThrow(() -> fundraiserDetailsService.deleteFundraiserDetails(1));
    }

    @Test
    void testDeleteFundraiserDetails_NotFound() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
            fundraiserDetailsService.deleteFundraiserDetails(1)
        );

        assertEquals(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testUpdateFundraiserDetails_Success() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.of(fundraiserDetails));
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(fundraiserDetailsRepository.save(any(FundraiserDetails.class))).thenReturn(fundraiserDetails);
        when(fundraiserDetailsMapper.toDTO(any(FundraiserDetails.class))).thenReturn(fundraiserDetailsDTO);

        assertNotNull(fundraiserDetailsService.updateFundraiserDetails(1, fundraiserDetailsDTO));
    }

    @Test
    void testUpdateFundraiserDetails_NotFound() {
        when(fundraiserDetailsRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
            fundraiserDetailsService.updateFundraiserDetails(1, fundraiserDetailsDTO)
        );

        assertEquals(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + "1", exception.getMessage());
    }
}

