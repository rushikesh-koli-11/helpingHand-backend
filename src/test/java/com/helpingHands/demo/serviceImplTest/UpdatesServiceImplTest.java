package com.helpingHands.demo.serviceImplTest;

import com.helpingHands.demo.DTO.UpdatesDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.UserConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.Updates;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.UpdatesMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UpdatesRepository;
import com.helpingHands.demo.services.serviceImpl.UpdatesServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdatesServiceImplTest {

    @Mock
    private UpdatesRepository updatesRepository;

    @Mock
    private FundraiserRepository fundraiserRepository;

    @InjectMocks
    private UpdatesServiceImpl updatesService;

    private UpdatesDTO updatesDTO;
    private Updates updates;
    private Fundraiser fundraiser;

    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
        fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setTitle("Medical Fundraiser");

        updatesDTO = new UpdatesDTO();
        updatesDTO.setUpdateId(1);
        updatesDTO.setFundraiserId(1);
        updatesDTO.setContent("Update content");

        updates = UpdatesMapper.toEntity(updatesDTO);
        updates.setUpdateId(1);
        updates.setFundraiser(fundraiser);
    }

    @Test
    void testPostUpdate_Success() {
        // Mock fundraiser
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        // Mock UpdatesDTO
        UpdatesDTO updatesDTO = new UpdatesDTO();
        updatesDTO.setFundraiserId(1);
        updatesDTO.setContent("New Update");
        
        // fixing Ensure `dateTime` is set to avoid NullPointerException
        updatesDTO.setCreatedAt(LocalDateTime.now().toString());

        // Mock Updates entity
        Updates updates = new Updates();
        updates.setUpdateId(1);
        updates.setContent("New Update");
        updates.setFundraiser(fundraiser);
        updates.setCreatedAt(LocalDateTime.now()); // fixing Ensure this is set

        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(updatesRepository.save(any(Updates.class))).thenReturn(updates);

        
        UpdatesDTO result = updatesService.postUpdate(updatesDTO);

        
        assertNotNull(result);
        assertEquals("New Update", result.getContent());

        verify(fundraiserRepository, times(1)).findById(1);
        verify(updatesRepository, times(1)).save(any(Updates.class));
    }


    @Test
    void testPostUpdate_FundraiserNotFound() {
        // Simulate that fundraiser is not found
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        // Verify that the expected CustomExceptions is thrown
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> updatesService.postUpdate(updatesDTO));

        assertEquals("Fundraiser not found with ID: 1", exception.getMessage());

        verify(fundraiserRepository, times(1)).findById(1);
        verify(updatesRepository, never()).save(any(Updates.class));
    }

    @Test
    void testGetAllUpdates_Success() {
        // Mock fundraiser entity
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1); 

        // Mock update entities
        Updates update1 = new Updates();
        update1.setUpdateId(1);
        update1.setContent("First Update");
        update1.setCreatedAt(LocalDateTime.now());
        update1.setFundraiser(fundraiser); 

        Updates update2 = new Updates();
        update2.setUpdateId(2);
        update2.setContent("Second Update");
        update2.setCreatedAt(LocalDateTime.now());
        update2.setFundraiser(fundraiser); 

        List<Updates> updatesList = Arrays.asList(update1, update2);

        when(updatesRepository.findAll()).thenReturn(updatesList);

        
        List<UpdatesDTO> result = updatesService.getAllUpdates();

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("First Update", result.get(0).getContent());
        assertEquals("Second Update", result.get(1).getContent());

        verify(updatesRepository, times(1)).findAll();
    }


    @Test
    void testGetAllUpdates_NoUpdatesFound() {
        when(updatesRepository.findAll()).thenReturn(Collections.emptyList());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> updatesService.getAllUpdates());
        assertEquals(UserConstants.NO_USERS_FOUND, exception.getMessage());

        verify(updatesRepository, times(1)).findAll();
    }

    @Test
    void testGetUpdateById_Success() {
        // Mock fundraiser entity
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1); 

        // Mock update entity
        Updates updates = new Updates();
        updates.setUpdateId(1);
        updates.setContent("Test Update");
        updates.setCreatedAt(LocalDateTime.now()); // fixing Ensure createdAt is set
        updates.setFundraiser(fundraiser); // fixing Ensure fundraiser is set

        when(updatesRepository.findById(1)).thenReturn(Optional.of(updates));

        
        UpdatesDTO result = updatesService.getUpdateById(1);

        
        assertNotNull(result);
        assertEquals(updates.getUpdateId(), result.getUpdateId());

        verify(updatesRepository, times(1)).findById(1);
    }


    @Test
    void testGetUpdateById_NotFound() {
        when(updatesRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> updatesService.getUpdateById(1));
        assertEquals(UserConstants.USER_NOT_FOUND, exception.getMessage());

        verify(updatesRepository, times(1)).findById(1);
    }

    @Test
    void testGetUpdatesByFundraiserId_Success() {
        // Mock fundraiser entity
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        // Create mock updates
        Updates update1 = new Updates();
        update1.setUpdateId(1);
        update1.setContent("Update 1");
        update1.setCreatedAt(LocalDateTime.now()); // fixing Ensure dateTime is not null
        update1.setFundraiser(fundraiser);

        Updates update2 = new Updates();
        update2.setUpdateId(2);
        update2.setContent("Update 2");
        update2.setCreatedAt(LocalDateTime.now()); // fixing Ensure dateTime is not null
        update2.setFundraiser(fundraiser);

        List<Updates> updatesList = List.of(update1, update2);
        fundraiser.setUpdates(updatesList);

        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));

        
        List<UpdatesDTO> result = updatesService.getUpdatesByFundraiserId(1);

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Update 1", result.get(0).getContent());
        assertEquals("Update 2", result.get(1).getContent());

        verify(fundraiserRepository, times(1)).findById(1);
    }


    @Test
    void testGetUpdatesByFundraiserId_FundraiserNotFound() {
        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> updatesService.getUpdatesByFundraiserId(1));
        assertEquals(FundraiserConstants.FUNDRAISER_NOT_FOUND + "1", exception.getMessage());

        verify(fundraiserRepository, times(1)).findById(1);
    }
}
