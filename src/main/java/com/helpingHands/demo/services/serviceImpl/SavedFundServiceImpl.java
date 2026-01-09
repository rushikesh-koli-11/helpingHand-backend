package com.helpingHands.demo.services.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpingHands.demo.DTO.SavedFundDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.SavedFund;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.SavedFundMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.SavedFundRepository;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.SavedFundService;

/**
 * Implementation of {@link SavedFundService} interface.
 * Provides business logic for managing saved funds.
 * @author Om Parshetti
 */
@Service
public class SavedFundServiceImpl implements SavedFundService {
	
    @Autowired
    private final SavedFundRepository repository;
	
    @Autowired
    private final SavedFundMapper savedFundMapper;
	
    @Autowired
    private final UserRepository userRepository;
	
    @Autowired
    private final FundraiserRepository fundraiserRepository;

    /**
     * Constructor for SavedFundServiceImpl.
     * @param repository Repository for saved funds.
     * @param savedFundMapper Mapper to convert between SavedFund and SavedFundDTO.
     * @param userRepository Repository for User entity.
     * @param fundraiserRepository Repository for Fundraiser entity.
     */
    public SavedFundServiceImpl(SavedFundRepository repository, SavedFundMapper savedFundMapper, UserRepository userRepository, FundraiserRepository fundraiserRepository) {
        this.repository = repository;
        this.savedFundMapper = savedFundMapper;
        this.userRepository = userRepository;
        this.fundraiserRepository = fundraiserRepository;
    }

    /**
     * Saves a new saved fund entry.
     * @param SavedFundDTO Data transfer object containing saved fund details.
     * @return The saved fund as a DTO.
     */
    @Override
    public SavedFundDTO saveFund(SavedFundDTO SavedFundDTO) {
        User user = userRepository.findById(SavedFundDTO.getUserId()).orElseThrow();
        Fundraiser fundraiser = fundraiserRepository.findById(SavedFundDTO.getFundraiserId()).orElseThrow();
        SavedFund SavedFund = savedFundMapper.toEntity(SavedFundDTO, user, fundraiser);
        return savedFundMapper.toDTO(repository.save(SavedFund));
    }

    /**
     * Updates an existing saved fund.
     * @param saveId The ID of the saved fund.
     * @param SavedFundDTO DTO containing updated saved fund details.
     * @return The updated saved fund as a DTO.
     */
    @Override
    public SavedFundDTO updateSavedFund(String saveId, SavedFundDTO SavedFundDTO) {
        SavedFund SavedFund = repository.findById(saveId).orElseThrow();
        User user = userRepository.findById(SavedFundDTO.getUserId()).orElseThrow();
        Fundraiser fundraiser = fundraiserRepository.findById(SavedFundDTO.getFundraiserId()).orElseThrow();
        SavedFund.setUser(user);
        SavedFund.setFundraiser(fundraiser);
        return savedFundMapper.toDTO(repository.save(SavedFund));
    }

    /**
     * Retrieves a saved fund by its ID.
     * @param saveId The ID of the saved fund.
     * @return The saved fund as a DTO.
     */
    @Override
    public SavedFundDTO getSavedFundById(String saveId) {
        return repository.findById(saveId)
            .map(savedFundMapper::toDTO)
            .orElseThrow();
    }

    /**
     * Retrieves all saved funds.
     * @return A list of saved fund DTOs.
     */
    @Override
    public List<SavedFundDTO> getAllSavedFunds() {
        return repository.findAll().stream()
            .map(savedFundMapper::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Deletes a saved fund by its ID.
     * @param saveId The ID of the saved fund to be deleted.
     */
    @Override
    public void deleteSavedFund(String saveId) {
        repository.deleteById(saveId);
    }
}
