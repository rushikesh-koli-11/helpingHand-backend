package com.helpingHands.demo.mapper;

import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.BackgroundDTO;
import com.helpingHands.demo.entities.Background;
import com.helpingHands.demo.entities.Fundraiser;

@Component
public class BackgroundMapper {

    public BackgroundDTO toDTO(Background background) {
    	if(background == null) {
    		return null;
    	}
    	
        BackgroundDTO dto = new BackgroundDTO();
        dto.setBackgroundId(background.getBackgroundId());
        if (background.getFundraiser() != null) {
            dto.setFundraiserId(background.getFundraiser().getId());
        }
        dto.setRelationWithPatient(background.getRelationWithPatient());
        dto.setMonthlyIncomeOfPatientsFamily(background.getMonthlyIncomeOfPatientsFamily());
        return dto;
    }

    public Background toEntity(BackgroundDTO dto, Fundraiser fundraiser) {
    	if(dto == null || fundraiser == null) {
    		return null;
    	}
        Background background = new Background();
        background.setBackgroundId(dto.getBackgroundId());
        background.setFundraiser(fundraiser);
        background.setRelationWithPatient(dto.getRelationWithPatient());
        background.setMonthlyIncomeOfPatientsFamily(dto.getMonthlyIncomeOfPatientsFamily());
        return background;
    }
}
