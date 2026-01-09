package com.helpingHands.demo.services.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpingHands.demo.DTO.UpdatesDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.UserConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.Updates;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.UpdatesMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UpdatesRepository;
import com.helpingHands.demo.services.UpdatesService;

@Service
public class UpdatesServiceImpl implements UpdatesService {

	@Autowired
	private FundraiserRepository fundraiserRepository;
	
	@Autowired
	private UpdatesRepository updatesRepository;


	public UpdatesServiceImpl(UpdatesRepository updatesRepository) {
		this.updatesRepository = updatesRepository;
	}

	@Override
	public UpdatesDTO postUpdate(UpdatesDTO updatesDTO) {
	    // Fetch fundraiser and ensure it exists
	    Fundraiser fundraiser = fundraiserRepository.findById(updatesDTO.getFundraiserId())
	            .orElseThrow(() -> new CustomExceptions("Fundraiser not found with ID: " + updatesDTO.getFundraiserId()));

	    // Map DTO to entity
	    Updates updates = UpdatesMapper.toEntity(updatesDTO);
	    updates.setFundraiser(fundraiser); // Set fundraiser here

	    // Save the update
	    Updates savedUpdate = updatesRepository.save(updates);
	    return UpdatesMapper.toDTO(savedUpdate);
	}


	@Override
	public List<UpdatesDTO> getAllUpdates() {
		List<Updates> updates = updatesRepository.findAll();
		if (updates.isEmpty()) {
            throw new CustomExceptions(UserConstants.NO_USERS_FOUND);
        }
		
		return updates.stream().map(UpdatesMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public UpdatesDTO getUpdateById(int updateId) {
		Updates updates = updatesRepository.findById(updateId)
				.orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND));
		return UpdatesMapper.toDTO(updates);
	}
	
	@Override
	public List<UpdatesDTO> getUpdatesByFundraiserId(int fundraiserId) {
	    Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
	            .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));

	    return fundraiser.getUpdates().stream()
	            .map(UpdatesMapper::toDTO) // Convert each Updates entity to UpdatesDTO
	            .collect(Collectors.toList()); // Collect into a List
	}


}
