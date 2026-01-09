package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.BackgroundDTO;
import com.helpingHands.demo.constants.BackgroundConstants;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.Background;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.BackgroundMapper;
import com.helpingHands.demo.repository.BackgroundRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Service implementation for Background Details.
*/
@Service
public class BackgroundServiceImpl implements BackgroundService {

    @Autowired
    private BackgroundRepository backgroundRepository;

    @Autowired
    private FundraiserRepository fundraiserRepository;

    @Autowired
    private BackgroundMapper backgroundMapper;

    /**
     * Creating a new background record for a fundraiser.
     */
    @Override
    public BackgroundDTO createBackground(BackgroundDTO backgroundDTO) {
        // Validating if fundraiser exists
        Fundraiser fundraiser = fundraiserRepository.findById(backgroundDTO.getFundraiserId())
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + backgroundDTO.getFundraiserId()));

        // Mapping and saving the background entity
        Background background = backgroundMapper.toEntity(backgroundDTO, fundraiser);
        background = backgroundRepository.save(background);
        return backgroundMapper.toDTO(background);
    }

    /**
     * Retrieving background details by fundraiser ID.
     */
    @Override
    public BackgroundDTO getBackgroundById(int fundraiserId) {
        // Checking if fundraiser exists
        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));

        // Retrieving background details
        Background background = backgroundRepository.findById(fundraiser.getBackground().getBackgroundId())
                .orElseThrow(() -> new CustomExceptions(
                        BackgroundConstants.BACKGROUND_NOT_FOUND + fundraiser.getBackground().getBackgroundId()));
        
        return backgroundMapper.toDTO(background);
    }

    /**
     * Deleting a background record by ID.
     */
    @Override
    public void deleteBackground(int backgroundId) {
        // Checking if background exists before deletion
        if (!backgroundRepository.existsById(backgroundId)) {
            throw new CustomExceptions(BackgroundConstants.BACKGROUND_NOT_FOUND + backgroundId);
        }
        // Deleting the background record
        backgroundRepository.deleteById(backgroundId);
    }

    /**
     * Updating an existing background record.
     */
    @Override
    public BackgroundDTO updateBackground(int id, BackgroundDTO backgroundDTO) {
        // Validating if background exists
        Background existingBackground = backgroundRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(BackgroundConstants.BACKGROUND_NOT_FOUND + id));

        // Updating background details
        existingBackground.setRelationWithPatient(backgroundDTO.getRelationWithPatient());
        existingBackground.setMonthlyIncomeOfPatientsFamily(backgroundDTO.getMonthlyIncomeOfPatientsFamily());

        // Saving updated background details
        Background updatedBackground = backgroundRepository.save(existingBackground);
        return backgroundMapper.toDTO(updatedBackground);
    }
}
