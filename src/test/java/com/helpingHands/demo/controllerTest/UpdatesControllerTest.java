package com.helpingHands.demo.controllerTest;

import com.helpingHands.demo.DTO.UpdatesDTO;
import com.helpingHands.demo.controller.UpdatesController;
import com.helpingHands.demo.services.UpdatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdatesControllerTest {

    @Mock
    private UpdatesService updatesService;

    @InjectMocks
    private UpdatesController updatesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    void testPostUpdate() {
        UpdatesDTO updatesDTO = UpdatesDTO.builder().build();
        updatesDTO.setUpdateId(1);
        updatesDTO.setContent("Test update");
        updatesDTO.setFundraiserId(1);

        when(updatesService.postUpdate(any(UpdatesDTO.class))).thenReturn(updatesDTO);

        ResponseEntity<UpdatesDTO> response = updatesController.postUpdate(updatesDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatesDTO, response.getBody());
        verify(updatesService, times(1)).postUpdate(updatesDTO);
    }

    @Test
    void testGetAllUpdates() {
        UpdatesDTO update1 = UpdatesDTO.builder().build();
        update1.setUpdateId(1);
        update1.setContent("Update 1");
        update1.setFundraiserId(1);

        UpdatesDTO update2 = UpdatesDTO.builder().build();
        update2.setUpdateId(2);
        update2.setContent("Update 2");
        update2.setFundraiserId(2);

        List<UpdatesDTO> updatesList = Arrays.asList(update1, update2);

        when(updatesService.getAllUpdates()).thenReturn(updatesList);

        ResponseEntity<List<UpdatesDTO>> response = updatesController.getAllUpdates();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatesList.size(), response.getBody().size());
        verify(updatesService, times(1)).getAllUpdates();
    }

    @Test
    void testGetUpdateById() {
        int updateId = 1;
        UpdatesDTO updatesDTO = UpdatesDTO.builder().build();
        updatesDTO.setUpdateId(updateId);
        updatesDTO.setContent("Test update");
        updatesDTO.setFundraiserId(1);

        when(updatesService.getUpdateById(updateId)).thenReturn(updatesDTO);

        ResponseEntity<UpdatesDTO> response = updatesController.getUpdateById(updateId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatesDTO, response.getBody());
        verify(updatesService, times(1)).getUpdateById(updateId);
    }

    @Test
    void testGetUpdatesByFundraiserId() {
        int fundraiserId = 1;

        UpdatesDTO update1 = UpdatesDTO.builder().build();
        update1.setUpdateId(1);
        update1.setContent("Update 1");
        update1.setFundraiserId(fundraiserId);

        UpdatesDTO update2 = UpdatesDTO.builder().build();
        update2.setUpdateId(2);
        update2.setContent("Update 2");
        update2.setFundraiserId(fundraiserId);

        List<UpdatesDTO> updatesList = Arrays.asList(update1, update2);

        when(updatesService.getUpdatesByFundraiserId(fundraiserId)).thenReturn(updatesList);

        ResponseEntity<List<UpdatesDTO>> response = updatesController.getUpdatesByFundraiserId(fundraiserId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatesList.size(), response.getBody().size());
        verify(updatesService, times(1)).getUpdatesByFundraiserId(fundraiserId);
    }
}
