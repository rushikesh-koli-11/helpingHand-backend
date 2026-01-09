package com.helpingHands.demo.mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.FundraiserDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;

@Component
public class FundraiserMapper {
	@Autowired
	public FundraiserDetailsMapper fundraiserDetailsMapper;

	public FundraiserDTO toDTO(Fundraiser fundraiser) {
	    if (fundraiser == null) {
	        return null;
	    }
	    
	    FundraiserDTO dto = new FundraiserDTO();
	    dto.setFundraiserId(fundraiser.getId());
	    dto.setTitle(fundraiser.getTitle());
	    dto.setDescription(fundraiser.getDescription());
	    dto.setGoalAmount(fundraiser.getGoalAmount());
	    dto.setCurrentAmount(fundraiser.getCurrentAmount());
	    dto.setStatus(fundraiser.getStatus());
	    dto.setMobileNumber(fundraiser.getMobileNumber());

	    // Ensure fundraiser.getUser() is not null before accessing it
	    if (fundraiser.getUser() != null) {
	        dto.setUserId(fundraiser.getUser().getUserId());
	    }

	    // Ensure fundraiserDetails is not null
	    dto.setFundraiserDetailsDTO(fundraiser.getFundraiserDetails() != null 
	        ? fundraiserDetailsMapper.toDTO(fundraiser.getFundraiserDetails()) 
	        : null);
	    
	    return dto;
	}

	public Fundraiser toEntity(FundraiserDTO dto, User user) {
	    if (dto == null) {
	        return null;
	    }
	    
	    Fundraiser fundraiser = new Fundraiser();
	    fundraiser.setId(dto.getFundraiserId());
	    fundraiser.setTitle(dto.getTitle());
	    fundraiser.setDescription(dto.getDescription());
	    fundraiser.setGoalAmount(dto.getGoalAmount());
	    fundraiser.setCurrentAmount(dto.getCurrentAmount());
	    fundraiser.setStatus(dto.getStatus());
	    fundraiser.setMobileNumber(dto.getMobileNumber());

	    // Set user only if provided
	    fundraiser.setUser(user);

	    return fundraiser;
	}

}
