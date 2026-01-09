package com.helpingHands.demo.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.helpingHands.demo.DTO.SavedFundDTO;
import com.helpingHands.demo.controller.SavedFundController;
import com.helpingHands.demo.services.SavedFundService;

@ExtendWith(MockitoExtension.class)
public class SavedFundControllerTest {

    @Mock
    private SavedFundService savedFundService;

    @InjectMocks
    private SavedFundController savedFundController;

    private SavedFundDTO savedFundDTO;

    @BeforeEach
    void setUp() {
        savedFundDTO = SavedFundDTO.builder()
                .saveId(1)
                .fundraiserId(1)
                .userId(200)
                .build();
    }

    @Test
    void testSaveFundraiser() {
        when(savedFundService.saveFund(any(SavedFundDTO.class))).thenReturn(savedFundDTO);
        ResponseEntity<SavedFundDTO> response = savedFundController.saveFundraiser(savedFundDTO);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(savedFundDTO, response.getBody());
    }

    @Test
    void testUpdateFundraiser() {
        when(savedFundService.updateSavedFund(eq(1), any(SavedFundDTO.class))).thenReturn(savedFundDTO);
        ResponseEntity<SavedFundDTO> response = savedFundController.updateFundraiser(1, savedFundDTO);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(savedFundDTO, response.getBody());
    }

    @Test
    void testGetFundraiser() {
        when(savedFundService.getSavedFundById(1)).thenReturn(savedFundDTO);
        ResponseEntity<SavedFundDTO> response = savedFundController.getFundraiser(1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(savedFundDTO, response.getBody());
    }

    @Test
    void testGetAllFundraisers() {
        List<SavedFundDTO> savedFundList = Arrays.asList(savedFundDTO);
        when(savedFundService.getAllSavedFunds()).thenReturn(savedFundList);
        ResponseEntity<List<SavedFundDTO>> response = savedFundController.getAllFundraisers();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testDeleteFundraiser() {
        doNothing().when(savedFundService).deleteSavedFund(1);
        ResponseEntity<Void> response = savedFundController.deleteFundraiser(1);
        assertEquals(204, response.getStatusCode().value());
        verify(savedFundService, times(1)).deleteSavedFund(1);
    }
}