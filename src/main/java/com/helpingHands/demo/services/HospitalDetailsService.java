package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;

public interface HospitalDetailsService {
	HospitalDetailsDTO createHospitalDetails(HospitalDetailsDTO hospitalDetailsDTO);
    HospitalDetailsDTO getHospitalDetailsByFundraiserId(String id);
    HospitalDetailsDTO getHospitalDetailsById(String id);
    List<HospitalDetailsDTO> getAllHospitalDetails(); 
    void deleteHospitalDetails(String id);
    HospitalDetailsDTO updateHospitalDetails(String id, HospitalDetailsDTO hospitalDetailsDTO);
}

