package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.UpdatesDTO;

public interface UpdatesService {
	
	UpdatesDTO postUpdate(UpdatesDTO updatesDTO);
	List<UpdatesDTO> getAllUpdates();
	UpdatesDTO getUpdateById(String updateId);
	List<UpdatesDTO> getUpdatesByFundraiserId(String fundraiserId);
}
