package com.helpingHands.demo.serviceImplTest;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.HospitalDetailsConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.HospitalDetails;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.HospitalDetailsMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.HospitalDetailsRepository;
import com.helpingHands.demo.services.serviceImpl.HospitalDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HospitalDetailsServiceImplTest {

    @InjectMocks
    private HospitalDetailsServiceImpl hospitalDetailsService;

    @Mock
    private HospitalDetailsRepository hospitalDetailsRepository;

    @Mock
    private FundraiserRepository fundraiserRepository;

    @Mock
    private HospitalDetailsMapper hospitalDetailsMapper;

    private HospitalDetails hospitalDetails;
    private HospitalDetailsDTO hospitalDetailsDTO;
    private Fundraiser fundraiser;

    @BeforeEach
    void setUp() {
        fundraiser = new Fundraiser();
        fundraiser.setId(1);
        
        hospitalDetails = new HospitalDetails();
        hospitalDetails.setId(1);
        hospitalDetails.setHospitalName("Test Hospital");
        hospitalDetails.setFundraiser(fundraiser);
        
        hospitalDetailsDTO = new HospitalDetailsDTO();
        hospitalDetailsDTO.setId(1);
        hospitalDetailsDTO.setHospitalName("Test Hospital");
        hospitalDetailsDTO.setFundraiserId(1);
    }

    @Test
    void testCreateHospitalDetails_Success() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(hospitalDetailsMapper.toEntity(hospitalDetailsDTO, fundraiser)).thenReturn(hospitalDetails);
        when(hospitalDetailsRepository.save(hospitalDetails)).thenReturn(hospitalDetails);
        when(hospitalDetailsMapper.toDTO(hospitalDetails)).thenReturn(hospitalDetailsDTO);

        HospitalDetailsDTO result = hospitalDetailsService.createHospitalDetails(hospitalDetailsDTO);

        assertNotNull(result);
        assertEquals(hospitalDetailsDTO.getHospitalName(), result.getHospitalName());
    }

    @Test
    void testCreateHospitalDetails_FundraiserNotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
                hospitalDetailsService.createHospitalDetails(hospitalDetailsDTO));

        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    void testGetHospitalDetailsByFundraiserId_Success() {
        fundraiser.setHospitalDetails(hospitalDetails);
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(hospitalDetailsRepository.findById(1)).thenReturn(Optional.of(hospitalDetails));
        when(hospitalDetailsMapper.toDTO(hospitalDetails)).thenReturn(hospitalDetailsDTO);

        HospitalDetailsDTO result = hospitalDetailsService.getHospitalDetailsByFundraiserId(1);
        assertNotNull(result);
        assertEquals(hospitalDetailsDTO.getHospitalName(), result.getHospitalName());
    }

    @Test
    void testGetHospitalDetailsByFundraiserId_NotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
                hospitalDetailsService.getHospitalDetailsByFundraiserId(1));
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    void testGetAllHospitalDetails_Success() {
        when(hospitalDetailsRepository.findAll()).thenReturn(Collections.singletonList(hospitalDetails));
        when(hospitalDetailsMapper.toDTO(hospitalDetails)).thenReturn(hospitalDetailsDTO);

        List<HospitalDetailsDTO> result = hospitalDetailsService.getAllHospitalDetails();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateHospitalDetails_Success() {
        when(hospitalDetailsRepository.findById(1)).thenReturn(Optional.of(hospitalDetails));
        when(hospitalDetailsRepository.save(hospitalDetails)).thenReturn(hospitalDetails);
        when(hospitalDetailsMapper.toDTO(hospitalDetails)).thenReturn(hospitalDetailsDTO);

        HospitalDetailsDTO result = hospitalDetailsService.updateHospitalDetails(1, hospitalDetailsDTO);

        assertNotNull(result);
        assertEquals(hospitalDetailsDTO.getHospitalName(), result.getHospitalName());
    }

    @Test
    void testUpdateHospitalDetails_NotFound() {
        when(hospitalDetailsRepository.findById(1)).thenReturn(Optional.empty());
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> 
                hospitalDetailsService.updateHospitalDetails(1, hospitalDetailsDTO));
        assertEquals(HospitalDetailsConstants.HOSPITAL_DETAILS_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    void testDeleteHospitalDetails_Success() {
        doNothing().when(hospitalDetailsRepository).deleteById(1);
        assertDoesNotThrow(() -> hospitalDetailsService.deleteHospitalDetails(1));
    }
}
