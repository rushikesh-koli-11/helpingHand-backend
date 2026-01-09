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

import com.helpingHands.demo.DTO.SavedFundDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.SavedFund;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.SavedFundMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.SavedFundRepository;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.serviceImpl.SavedFundServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SavedFundServiceImplTest {

    @Mock
    private SavedFundRepository repository;

    @Mock
    private SavedFundMapper savedFundMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FundraiserRepository fundraiserRepository;

    @InjectMocks
    private SavedFundServiceImpl savedFundService;

    private SavedFund savedFund;
    private SavedFundDTO savedFundDTO;
    private User user;
    private Fundraiser fundraiser;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);

        fundraiser = new Fundraiser();
        fundraiser.setId(1);

        savedFund = new SavedFund();
        savedFund.setSaveId(1);
        savedFund.setUser(user);
        savedFund.setFundraiser(fundraiser);

        savedFundDTO = new SavedFundDTO();
        savedFundDTO.setSaveId(1);
        savedFundDTO.setUserId(1);
        savedFundDTO.setFundraiserId(1);
    }

    @Test
    void testSaveFund() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(savedFundMapper.toEntity(savedFundDTO, user, fundraiser)).thenReturn(savedFund);
        when(repository.save(savedFund)).thenReturn(savedFund);
        when(savedFundMapper.toDTO(savedFund)).thenReturn(savedFundDTO);

        SavedFundDTO result = savedFundService.saveFund(savedFundDTO);

        assertNotNull(result);
        assertEquals(1, result.getSaveId());
        verify(repository, times(1)).save(savedFund);
    }

    @Test
    void testUpdateSavedFund() {
        when(repository.findById(1)).thenReturn(Optional.of(savedFund));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(repository.save(savedFund)).thenReturn(savedFund);
        when(savedFundMapper.toDTO(savedFund)).thenReturn(savedFundDTO);

        SavedFundDTO result = savedFundService.updateSavedFund(1, savedFundDTO);

        assertNotNull(result);
        assertEquals(1, result.getSaveId());
        verify(repository, times(1)).save(savedFund);
    }

    @Test
    void testGetSavedFundById() {
        when(repository.findById(1)).thenReturn(Optional.of(savedFund));
        when(savedFundMapper.toDTO(savedFund)).thenReturn(savedFundDTO);

        SavedFundDTO result = savedFundService.getSavedFundById(1);

        assertNotNull(result);
        assertEquals(1, result.getSaveId());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testGetAllSavedFunds() {
        List<SavedFund> savedFundList = Arrays.asList(savedFund);
//        List<SavedFundDTO> savedFundDTOList = Arrays.asList(savedFundDTO);

        when(repository.findAll()).thenReturn(savedFundList);
        when(savedFundMapper.toDTO(savedFund)).thenReturn(savedFundDTO);

        List<SavedFundDTO> result = savedFundService.getAllSavedFunds();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testDeleteSavedFund() {
        doNothing().when(repository).deleteById(1);

        savedFundService.deleteSavedFund(1);

        verify(repository, times(1)).deleteById(1);
    }
}
