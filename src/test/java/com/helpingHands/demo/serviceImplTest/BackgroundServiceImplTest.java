package com.helpingHands.demo.serviceImplTest;

import com.helpingHands.demo.DTO.BackgroundDTO;
import com.helpingHands.demo.constants.BackgroundConstants;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.Background;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.BackgroundMapper;
import com.helpingHands.demo.repository.BackgroundRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.serviceImpl.BackgroundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BackgroundServiceImplTest {

    @Mock
    private BackgroundRepository backgroundRepository;
    
    @Mock
    private FundraiserRepository fundraiserRepository;
    
    @Mock
    private BackgroundMapper backgroundMapper;
    
    @InjectMocks
    private BackgroundServiceImpl backgroundService;
    
    private Background background;
    private Fundraiser fundraiser;
    private BackgroundDTO backgroundDTO;

    @BeforeEach
    void setUp() {
        fundraiser = new Fundraiser();
        fundraiser.setId(1);

        background = new Background();
        background.setBackgroundId(1);
        background.setRelationWithPatient("Brother");
        background.setMonthlyIncomeOfPatientsFamily(15000.0);
        background.setFundraiser(fundraiser);

        backgroundDTO = new BackgroundDTO();
        backgroundDTO.setFundraiserId(1);
        backgroundDTO.setRelationWithPatient("Brother");
        backgroundDTO.setMonthlyIncomeOfPatientsFamily(15000.0);
    }

    @Test
    void testCreateBackground_Success() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(backgroundMapper.toEntity(any(BackgroundDTO.class), any(Fundraiser.class))).thenReturn(background);
        when(backgroundRepository.save(any(Background.class))).thenReturn(background);
        when(backgroundMapper.toDTO(any(Background.class))).thenReturn(backgroundDTO);

        BackgroundDTO result = backgroundService.createBackground(backgroundDTO);

        assertNotNull(result);
        assertEquals("Brother", result.getRelationWithPatient());
        verify(backgroundRepository, times(1)).save(any(Background.class));
    }

    @Test
    void testCreateBackground_FundraiserNotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(CustomExceptions.class, () -> backgroundService.createBackground(backgroundDTO));
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testGetBackgroundById_Success() {
        fundraiser.setBackground(background);
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(backgroundRepository.findById(1)).thenReturn(Optional.of(background));
        when(backgroundMapper.toDTO(any(Background.class))).thenReturn(backgroundDTO);

        BackgroundDTO result = backgroundService.getBackgroundById(1);

        assertNotNull(result);
        assertEquals("Brother", result.getRelationWithPatient());
    }

    @Test
    void testGetBackgroundById_FundraiserNotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(CustomExceptions.class, () -> backgroundService.getBackgroundById(1));
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testDeleteBackground_Success() {
        when(backgroundRepository.existsById(1)).thenReturn(true);
        doNothing().when(backgroundRepository).deleteById(1);

        assertDoesNotThrow(() -> backgroundService.deleteBackground(1));
        verify(backgroundRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteBackground_NotFound() {
        when(backgroundRepository.existsById(1)).thenReturn(false);
        Exception exception = assertThrows(CustomExceptions.class, () -> backgroundService.deleteBackground(1));
        assertEquals(BackgroundConstants.BACKGROUND_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testUpdateBackground_Success() {
        when(backgroundRepository.findById(1)).thenReturn(Optional.of(background));
        when(backgroundRepository.save(any(Background.class))).thenReturn(background);
        when(backgroundMapper.toDTO(any(Background.class))).thenReturn(backgroundDTO);

        BackgroundDTO result = backgroundService.updateBackground(1, backgroundDTO);

        assertNotNull(result);
        assertEquals("Brother", result.getRelationWithPatient());
        verify(backgroundRepository, times(1)).save(any(Background.class));
    }

    @Test
    void testUpdateBackground_NotFound() {
        when(backgroundRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(CustomExceptions.class, () -> backgroundService.updateBackground(1, backgroundDTO));
        assertEquals(BackgroundConstants.BACKGROUND_NOT_FOUND + "1", exception.getMessage());
    }
}
