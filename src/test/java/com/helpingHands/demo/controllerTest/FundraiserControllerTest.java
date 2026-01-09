package com.helpingHands.demo.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import com.helpingHands.demo.DTO.FundraiserDTO;
import com.helpingHands.demo.controller.FundraiserController;
import com.helpingHands.demo.services.FundraiserService;

@ExtendWith(MockitoExtension.class)
public class FundraiserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FundraiserService fundraiserService;

    @InjectMocks
    private FundraiserController fundraiserController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fundraiserController).build();
    }

    @Test
    void testCreateFundraiser() throws Exception {
        FundraiserDTO fundraiserDTO = new FundraiserDTO();
        when(fundraiserService.createFundraiser(any())).thenReturn(fundraiserDTO);

        mockMvc.perform(post("/fundraisers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fundraiserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllFundraisers() throws Exception {
        List<FundraiserDTO> fundraisers = Arrays.asList(new FundraiserDTO());
        when(fundraiserService.getAllFundraisers()).thenReturn(fundraisers);

        mockMvc.perform(get("/fundraisers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetFundraiserById() throws Exception {
        FundraiserDTO fundraiserDTO = new FundraiserDTO();
        when(fundraiserService.getFundraiserById(anyInt())).thenReturn(fundraiserDTO);

        mockMvc.perform(get("/fundraisers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllFundraisersExceptUserId() throws Exception {
        List<FundraiserDTO> fundraisers = Arrays.asList(new FundraiserDTO());
        when(fundraiserService.getAllFundraisersExceptUserId(anyInt())).thenReturn(fundraisers);

        mockMvc.perform(get("/fundraisers/view-fundraisers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetLatestFundraiser() throws Exception {
        FundraiserDTO fundraiserDTO = new FundraiserDTO();
        when(fundraiserService.getLatestFundraiser()).thenReturn(fundraiserDTO);

        mockMvc.perform(get("/fundraisers/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteFundraiser() throws Exception {
        doNothing().when(fundraiserService).deleteFundraiser(anyInt());

        mockMvc.perform(delete("/fundraisers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testApproveOrRejectFundraiser() throws Exception {
        doNothing().when(fundraiserService).updateApprovalStatus(anyInt(), anyString());

        mockMvc.perform(patch("/fundraisers/1/approval")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("status", "approved"))))
                .andExpect(status().isOk())
                .andExpect(content().string("Fundraiser approved successfully."));
    }
}