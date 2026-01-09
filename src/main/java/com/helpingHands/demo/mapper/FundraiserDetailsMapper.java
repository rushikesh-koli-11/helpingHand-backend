package com.helpingHands.demo.mapper;
import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;

@Component
public class FundraiserDetailsMapper {

	public FundraiserDetailsDTO toDTO(FundraiserDetails entity) {
	    if (entity == null) {
	        return null;
	    }
	    FundraiserDetailsDTO dto = new FundraiserDetailsDTO();
	    dto.setId(entity.getId());
	    dto.setFundraiserId(entity.getFundraiser() != null ? entity.getFundraiser().getId() : 0); // Avoid NPE
	    dto.setCoverPicture(entity.getCoverPicture());
	    dto.setVideoAppeal(entity.getVideoAppeal());
	    dto.setRemainingAmount(entity.getRemainingAmount());
	    dto.setPatientName(entity.getPatientName());
	    dto.setPatientAge(entity.getPatientAge());
	    dto.setPatientGender(entity.getPatientGender());
	    dto.setMedicalCondition(entity.getMedicalCondition());
	    dto.setStory(entity.getStory());
	    return dto;
	}

	public FundraiserDetails toEntity(FundraiserDetailsDTO dto, Fundraiser fundraiser) {
	    if (dto == null) {
	        return null;
	    }
	    FundraiserDetails entity = new FundraiserDetails();
	    entity.setId(dto.getId());
	    entity.setFundraiser(fundraiser); // Ensure fundraiser is not null when passed
	    entity.setCoverPicture(dto.getCoverPicture());
	    entity.setVideoAppeal(dto.getVideoAppeal());
	    entity.setRemainingAmount(dto.getRemainingAmount());
	    entity.setPatientName(dto.getPatientName());
	    entity.setPatientAge(dto.getPatientAge());
	    entity.setPatientGender(dto.getPatientGender());
	    entity.setMedicalCondition(dto.getMedicalCondition());
	    entity.setStory(dto.getStory());
	    return entity;
	}

}
