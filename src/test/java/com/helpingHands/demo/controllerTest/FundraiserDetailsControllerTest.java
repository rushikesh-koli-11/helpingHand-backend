package com.helpingHands.demo.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.controller.FundraiserDetailsController;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.globalException.Response;
import com.helpingHands.demo.services.FundraiserDetailsService;

@ExtendWith(MockitoExtension.class)
public class FundraiserDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FundraiserDetailsService fundraiserDetailsService;

    @InjectMocks
    private FundraiserDetailsController fundraiserDetailsController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fundraiserDetailsController).build();
    }

    @Test
    void testCreateFundraiserDetails() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "test image".getBytes());
        FundraiserDetails fundraiserDetails = new FundraiserDetails();
        Response<FundraiserDetails> response = new Response<>();
        response.setData(fundraiserDetails);

        when(fundraiserDetailsService.createFundraiserDetails(any(), anyInt(), anyString(), anyString(), anyInt(), anyString(), anyString(), anyString()))
            .thenReturn(response);

        mockMvc.perform(multipart("/fundraiser-details")
                .file(file)
                .param("fundraiserId", "1")
                .param("videoAppeal", "www.youtube.com")
                .param("patientName", "Varun Kulkarni")
                .param("patientAge", "30")
                .param("patientGender", "Male")
                .param("medicalCondition", "Critical Condition")
                .param("story", "My Story")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateFundraiserDetailsWithInvalidInput() throws Exception {
        mockMvc.perform(multipart("/fundraiser-details")
                .param("fundraiserId", "")
                .param("videoAppeal", "")
                .param("patientName", "")
                .param("patientAge", "")
                .param("patientGender", "")
                .param("medicalCondition", "")
                .param("story", "")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllFundraiserDetails() throws Exception {
        when(fundraiserDetailsService.getAllFundraiserDetails()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/fundraiser-details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetFundraiserDetailsByIdNotFound() throws Exception {
        when(fundraiserDetailsService.getFundraiserDetailsById(anyInt())).thenReturn(null);
        mockMvc.perform(get("/fundraiser-details/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteFundraiserDetails() throws Exception {
        doNothing().when(fundraiserDetailsService).deleteFundraiserDetails(anyInt());
        mockMvc.perform(delete("/fundraiser-details/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateFundraiserDetails() throws Exception {
        FundraiserDetailsDTO dto = new FundraiserDetailsDTO();
        when(fundraiserDetailsService.updateFundraiserDetails(anyInt(), any())).thenReturn(dto);

        mockMvc.perform(put("/fundraiser-details/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDownloadFileAsBase64() throws Exception {
        byte[] imageBytes = "testImageData".getBytes();
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        FundraiserDetailsDTO dto = new FundraiserDetailsDTO();
        dto.setCoverPicture(imageBytes);
        when(fundraiserDetailsService.getFundraiserDetailsById(anyInt())).thenReturn(dto);

        mockMvc.perform(get("/fundraiser-details/download/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(base64String));
    }

    @Test
    void testDownloadFileAsBase64NotFound() throws Exception {
        when(fundraiserDetailsService.getFundraiserDetailsById(anyInt())).thenReturn(null);
        mockMvc.perform(get("/fundraiser-details/download/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testConvertBase64ToImage() throws Exception {
        String base64Data = Base64.getEncoder().encodeToString("imageData".getBytes());
        byte[] expectedImageData = Base64.getDecoder().decode(base64Data);

        mockMvc.perform(post("/fundraiser-details/convert-to-image")
                .contentType(MediaType.APPLICATION_JSON)
                .content(base64Data)) // Send raw string
                .andExpect(status().isOk())
                .andExpect(content().bytes(expectedImageData));
    }


    @Test
    void testConvertBase64ToImageInvalid() throws Exception {
        mockMvc.perform(post("/fundraiser-details/convert-to-image")
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalidBase64"))
                .andExpect(status().isBadRequest());
    }
}
