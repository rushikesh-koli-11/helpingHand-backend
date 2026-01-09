package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.SavedFundDTO;

public interface SavedFundService {
	SavedFundDTO saveFund(SavedFundDTO SavedFundDTO);
    SavedFundDTO updateSavedFund(int saveId, SavedFundDTO SavedFundDTO);
    SavedFundDTO getSavedFundById(int saveId);
    List<SavedFundDTO> getAllSavedFunds();
    void deleteSavedFund(int saveId);
}
