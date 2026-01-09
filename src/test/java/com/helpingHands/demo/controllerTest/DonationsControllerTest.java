package com.helpingHands.demo.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.controller.DonationsController;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.globalException.Response;
import com.helpingHands.demo.services.DonationsService;

@ExtendWith(MockitoExtension.class)
public class DonationsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DonationsService donationsService;

    @InjectMocks
    private DonationsController donationsController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(donationsController).build();
    }

    @Test
    void testSaveDonation() throws Exception {
        DonationsDTO donationsDTO = new DonationsDTO();
        Response<DonationsDTO> response = new Response<>();
        response.setData(donationsDTO);

        when(donationsService.saveDonation(any())).thenReturn(response);

        mockMvc.perform(post("/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donationsDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetDonationById() throws Exception {
        DonationsDTO donationsDTO = new DonationsDTO();
        when(donationsService.getDonationById(anyInt())).thenReturn(donationsDTO);

        mockMvc.perform(get("/donations/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllDonationsByUserId() throws Exception {
        List<DonationsDTO> donationsList = Arrays.asList(new DonationsDTO());
        when(donationsService.getDonationsByUserId(anyInt())).thenReturn(donationsList);

        mockMvc.perform(get("/donations/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllDonationsByFundraiserId() throws Exception {
        List<DonationsDTO> donationsList = Arrays.asList(new DonationsDTO());
        when(donationsService.getDonationsByFundraiserId(anyInt())).thenReturn(donationsList);

        mockMvc.perform(get("/donations/fundraiser/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testMarkSuccess() throws Exception {
        doNothing().when(donationsService).updateDonationStatus(anyInt(), eq(DonationStatus.SUCCESS));

        mockMvc.perform(get("/donations/success").param("donationId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Donation marked as SUCCESS"));
    }

    @Test
    void testMarkCancel() throws Exception {
        doNothing().when(donationsService).updateDonationStatus(anyInt(), eq(DonationStatus.CANCEL));

        mockMvc.perform(get("/donations/cancel").param("donationId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Donation marked as CANCEL"));
    }
}
