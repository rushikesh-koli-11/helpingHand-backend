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

import com.helpingHands.demo.DTO.BankDetailsDTO;
import com.helpingHands.demo.constants.BankDetailsConstants;
import com.helpingHands.demo.entities.BankDetails;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.BankDetailsMapper;
import com.helpingHands.demo.repository.BankDetailsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.serviceImpl.BankDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BankDetailsServiceImplTest {

    @Mock
    private BankDetailsRepository bankDetailsRepository;
    
    @Mock
    private BankDetailsMapper bankDetailsMapper;
    
    @Mock
    private FundraiserRepository fundraiserRepository;
    
    @InjectMocks
    private BankDetailsServiceImpl bankDetailsService;

    private BankDetails bankDetails;
    private BankDetailsDTO bankDetailsDTO;
    private Fundraiser fundraiser;

    @BeforeEach
    void setUp() {
        bankDetails = new BankDetails();
        bankDetails.setBankId(1);
        bankDetails.setAccountNumber("1234567890");

        bankDetailsDTO = new BankDetailsDTO();
        bankDetailsDTO.setBankId(1);
        bankDetailsDTO.setAccountNumber("1234567890");

        fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setBankDetails(bankDetails);
    }

    @Test
    void testGetAllBankDetails_Success() {
        when(bankDetailsRepository.findAll()).thenReturn(Arrays.asList(bankDetails));
        when(bankDetailsMapper.toDTO(bankDetails)).thenReturn(bankDetailsDTO);

        List<BankDetailsDTO> result = bankDetailsService.getAllBankDetails();
        assertEquals(1, result.size());
        assertEquals("1234567890", result.get(0).getAccountNumber());
    }

    @Test
    void testGetAllBankDetails_Exception() {
        when(bankDetailsRepository.findAll()).thenThrow(new RuntimeException());
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> bankDetailsService.getAllBankDetails());
        assertEquals(BankDetailsConstants.DATABASE_ERROR, exception.getMessage());
    }

    @Test
    void testGetBankDetailsByFundraiserId_Success() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(bankDetailsRepository.findById(1)).thenReturn(Optional.of(bankDetails));
        when(bankDetailsMapper.toDTO(bankDetails)).thenReturn(bankDetailsDTO);

        BankDetailsDTO result = bankDetailsService.getBankDetailsByFundraiserId(1);
        assertNotNull(result);
        assertEquals("1234567890", result.getAccountNumber());
    }

    @Test
    void testGetBankDetailsByFundraiserId_NotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> bankDetailsService.getBankDetailsByFundraiserId(1));
        assertTrue(exception.getMessage().contains("Fundraiser not found"));
    }

    @Test
    void testCreateBankDetails_Success() {
        when(bankDetailsMapper.toEntity(bankDetailsDTO)).thenReturn(bankDetails);
        when(bankDetailsRepository.save(bankDetails)).thenReturn(bankDetails);
        when(bankDetailsMapper.toDTO(bankDetails)).thenReturn(bankDetailsDTO);

        BankDetailsDTO result = bankDetailsService.createBankDetails(bankDetailsDTO);
        assertNotNull(result);
        assertEquals("1234567890", result.getAccountNumber());
    }

    @Test
    void testCreateBankDetails_InvalidData() {
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> bankDetailsService.createBankDetails(null));
        assertTrue(exception.getMessage().contains("Invalid data"));
    }

    @Test
    void testUpdateBankDetails_Success() {
        when(bankDetailsRepository.findById(1)).thenReturn(Optional.of(bankDetails));
        when(bankDetailsMapper.toEntity(bankDetailsDTO)).thenReturn(bankDetails);
        when(bankDetailsRepository.save(bankDetails)).thenReturn(bankDetails);
        when(bankDetailsMapper.toDTO(bankDetails)).thenReturn(bankDetailsDTO);

        BankDetailsDTO result = bankDetailsService.updateBankDetails(1, bankDetailsDTO);
        assertNotNull(result);
        assertEquals("1234567890", result.getAccountNumber());
    }

    @Test
    void testUpdateBankDetails_NotFound() {
        when(bankDetailsRepository.findById(1)).thenReturn(Optional.empty());
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> bankDetailsService.updateBankDetails(1, bankDetailsDTO));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void testDeleteBankDetails_Success() {
        when(bankDetailsRepository.existsById(1)).thenReturn(true);
        doNothing().when(bankDetailsRepository).deleteById(1);
        assertDoesNotThrow(() -> bankDetailsService.deleteBankDetails(1));
    }

    @Test
    void testDeleteBankDetails_NotFound() {
        when(bankDetailsRepository.existsById(1)).thenReturn(false);
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> bankDetailsService.deleteBankDetails(1));
        assertTrue(exception.getMessage().contains("not found"));
    }
}
