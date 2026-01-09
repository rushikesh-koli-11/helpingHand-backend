package com.helpingHands.demo.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpingHands.demo.DTO.BackgroundDTO;
import com.helpingHands.demo.controller.BackgroundController;
import com.helpingHands.demo.services.BackgroundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BackgroundControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BackgroundService backgroundService;

    @InjectMocks
    private BackgroundController backgroundController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(backgroundController).build();
    }

    @Test
    void testCreateBackground() throws Exception {
        BackgroundDTO backgroundDTO = new BackgroundDTO(1, 1, "Father", 5000.0);
        when(backgroundService.createBackground(any(BackgroundDTO.class))).thenReturn(backgroundDTO);

        mockMvc.perform(post("/backgrounds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(backgroundDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.backgroundId").value(1))
                .andExpect(jsonPath("$.fundraiserId").value(1))
                .andExpect(jsonPath("$.relationWithPatient").value("Father"))
                .andExpect(jsonPath("$.monthlyIncomeOfPatientsFamily").value(5000.0));
    }

    @Test
    void testGetBackgroundById() throws Exception {
        BackgroundDTO backgroundDTO = new BackgroundDTO(1, 1, "Father", 5000.0);
        when(backgroundService.getBackgroundById(1)).thenReturn(backgroundDTO);

        mockMvc.perform(get("/backgrounds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.backgroundId").value(1))
                .andExpect(jsonPath("$.fundraiserId").value(1))
                .andExpect(jsonPath("$.relationWithPatient").value("Father"))
                .andExpect(jsonPath("$.monthlyIncomeOfPatientsFamily").value(5000.0));
    }

    @Test
    void testDeleteBackground() throws Exception {
        doNothing().when(backgroundService).deleteBackground(1);

        mockMvc.perform(delete("/backgrounds/1"))
                .andExpect(status().isOk());

        verify(backgroundService, times(1)).deleteBackground(1);
    }

    @Test
    void testUpdateBackground() throws Exception {
        BackgroundDTO updatedBackground = new BackgroundDTO(1, 1, "Brother", 7000.0);
        when(backgroundService.updateBackground(eq(1), any(BackgroundDTO.class))).thenReturn(updatedBackground);

        mockMvc.perform(put("/backgrounds/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBackground)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.backgroundId").value(1))
                .andExpect(jsonPath("$.fundraiserId").value(1))
                .andExpect(jsonPath("$.relationWithPatient").value("Brother"))
                .andExpect(jsonPath("$.monthlyIncomeOfPatientsFamily").value(7000.0));
    }
}