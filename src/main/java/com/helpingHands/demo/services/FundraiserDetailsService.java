package com.helpingHands.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.globalException.Response;

public interface FundraiserDetailsService {
	List<FundraiserDetailsDTO> getAllFundraiserDetails();

	FundraiserDetailsDTO getFundraiserDetailsById(String id);

	void deleteFundraiserDetails(String id);

	FundraiserDetailsDTO updateFundraiserDetails(String id, FundraiserDetailsDTO dto);

	FundraiserDetailsDTO getCoverImageById(String id);

	void saveCoverImage(FundraiserDetailsDTO fundraiserDetailsDTO);

	Response<FundraiserDetails> createFundraiserDetails(MultipartFile coverPicture, String fundraiserId,
			String videoAppeal, String patientName, Integer patientAge, String patientGender, String medicalCondition,
			String story) throws IOException;
}
