package com.helpingHands.demo.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpingHands.demo.DTO.BankDetailsDTO;
import com.helpingHands.demo.controller.BankDetailsController;
import com.helpingHands.demo.services.BankDetailsService;

@ExtendWith(MockitoExtension.class)
public class BankDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BankDetailsService bankDetailsService;

    @InjectMocks
    private BankDetailsController bankDetailsController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankDetailsController).build();
    }

    @Test
    void testGetAllBankDetails() throws Exception {
        List<BankDetailsDTO> bankDetailsList = Arrays.asList(new BankDetailsDTO());
        when(bankDetailsService.getAllBankDetails()).thenReturn(bankDetailsList);

        mockMvc.perform(get("/bank-details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetBankDetailsById() throws Exception {
        BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();
        when(bankDetailsService.getBankDetailsByFundraiserId(anyInt())).thenReturn(bankDetailsDTO);

        mockMvc.perform(get("/bank-details/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateBankDetails() throws Exception {
        BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();
        when(bankDetailsService.createBankDetails(any())).thenReturn(bankDetailsDTO);

        mockMvc.perform(post("/bank-details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bankDetailsDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBankDetails() throws Exception {
        BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();
        when(bankDetailsService.updateBankDetails(anyInt(), any())).thenReturn(bankDetailsDTO);

        mockMvc.perform(put("/bank-details/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bankDetailsDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBankDetails() throws Exception {
        doNothing().when(bankDetailsService).deleteBankDetails(anyInt());

        mockMvc.perform(delete("/bank-details/1"))
                .andExpect(status().isOk());
    }
}