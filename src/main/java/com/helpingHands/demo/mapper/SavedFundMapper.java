package com.helpingHands.demo.mapper;

import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.SavedFundDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.SavedFund;
import com.helpingHands.demo.entities.User;

@Component
public class SavedFundMapper {
	
	public SavedFundDTO toDTO(SavedFund savedFund) {
		if (savedFund == null) {
			return null;
		}
		SavedFundDTO savedFundDTO = new SavedFundDTO();
		savedFundDTO.setSaveId(savedFund.getSaveId());
		if (savedFund.getUser() != null) {
			savedFundDTO.setUserId(savedFund.getUser().getUserId());
		}
		if (savedFund.getFundraiser() != null) {
			savedFundDTO.setFundraiserId(savedFund.getFundraiser().getId());
		}
		return savedFundDTO;
	}
	
	public SavedFund toEntity(SavedFundDTO savedFundDTO, User user, Fundraiser fundraiser) {
		SavedFund savedFund = new SavedFund();
		savedFund.setSaveId(savedFundDTO.getSaveId());
		savedFund.setUser(user);
		savedFund.setFundraiser(fundraiser);
		return savedFund;
	}

}
