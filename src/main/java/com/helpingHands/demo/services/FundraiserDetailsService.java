package com.helpingHands.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.globalException.Response;

public interface FundraiserDetailsService {
//	FundraiserDetailsDTO createFundraiserDetails(FundraiserDetailsDTO dto);
	List<FundraiserDetailsDTO> getAllFundraiserDetails();

	FundraiserDetailsDTO getFundraiserDetailsById(int id);

	void deleteFundraiserDetails(int id);

	FundraiserDetailsDTO updateFundraiserDetails(int id, FundraiserDetailsDTO dto);

	FundraiserDetailsDTO getCoverImageById(int id);

	void saveCoverImage(FundraiserDetailsDTO fundraiserDetailsDTO);

	Response<FundraiserDetails> createFundraiserDetails(MultipartFile coverPicture, int fundraiserId,
			String videoAppeal, String patientName, Integer patientAge, String patientGender, String medicalCondition,
			String story) throws IOException;
}
