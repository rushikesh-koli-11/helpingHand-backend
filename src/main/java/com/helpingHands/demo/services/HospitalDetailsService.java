package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;

public interface HospitalDetailsService {
	HospitalDetailsDTO createHospitalDetails(HospitalDetailsDTO hospitalDetailsDTO);
    HospitalDetailsDTO getHospitalDetailsByFundraiserId(int id);
    List<HospitalDetailsDTO> getAllHospitalDetails(); 
    void deleteHospitalDetails(int id);
    HospitalDetailsDTO updateHospitalDetails(int id, HospitalDetailsDTO hospitalDetailsDTO);
}

