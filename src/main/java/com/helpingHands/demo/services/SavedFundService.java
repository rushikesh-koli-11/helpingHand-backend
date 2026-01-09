package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.SavedFundDTO;

public interface SavedFundService {
	SavedFundDTO saveFund(SavedFundDTO SavedFundDTO);
    SavedFundDTO updateSavedFund(String saveId, SavedFundDTO SavedFundDTO);
    SavedFundDTO getSavedFundById(String saveId);
    List<SavedFundDTO> getAllSavedFunds();
    void deleteSavedFund(String saveId);
}
