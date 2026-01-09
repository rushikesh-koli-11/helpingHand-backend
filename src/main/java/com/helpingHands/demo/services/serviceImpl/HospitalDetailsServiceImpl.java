package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.HospitalDetailsConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.HospitalDetails;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.HospitalDetailsMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.HospitalDetailsRepository;
import com.helpingHands.demo.services.HospitalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing hospital details.
 * This class provides functionality for creating, retrieving, updating, and deleting hospital details
 * associated with fundraisers.
 */
@Service
public class HospitalDetailsServiceImpl implements HospitalDetailsService {

    @Autowired
    private HospitalDetailsRepository hospitalDetailsRepository;

    @Autowired
    private FundraiserRepository fundraiserRepository;

    @Autowired
    private HospitalDetailsMapper hospitalDetailsMapper;

    /**
     * Creates and saves hospital details associated with a fundraiser.
     *
     * @param hospitalDetailsDTO The {@link HospitalDetailsDTO} containing hospital details.
     * @return The saved hospital details as a {@link HospitalDetailsDTO}.
     * @throws CustomExceptions If the associated fundraiser is not found.
     */
    @Override
    public HospitalDetailsDTO createHospitalDetails(HospitalDetailsDTO hospitalDetailsDTO) {
        Fundraiser fundraiser = fundraiserRepository.findById(hospitalDetailsDTO.getFundraiserId())
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + hospitalDetailsDTO.getFundraiserId()));

        HospitalDetails hospitalDetails = hospitalDetailsMapper.toEntity(hospitalDetailsDTO, fundraiser);
        hospitalDetails = hospitalDetailsRepository.save(hospitalDetails);

        return hospitalDetailsMapper.toDTO(hospitalDetails);
    }

    /**
     * Retrieves hospital details by fundraiser ID.
     *
     * @param fundraiserId The ID of the fundraiser associated with the hospital details.
     * @return The {@link HospitalDetailsDTO} if found, otherwise null.
     * @throws CustomExceptions If the fundraiser or hospital details are not found.
     */
    @Override
    public HospitalDetailsDTO getHospitalDetailsByFundraiserId(String fundraiserId) {
        HospitalDetails hospitalDetails = hospitalDetailsRepository.findByFundraiserId(fundraiserId)
                .orElse(null);

        if (hospitalDetails == null) {
            System.out.println(HospitalDetailsConstants.HOSPITAL_DETAILS_NOT_FOUND + fundraiserId);
            return null;
        }

        return hospitalDetailsMapper.toDTO(hospitalDetails);
    }

    @Override
    public HospitalDetailsDTO getHospitalDetailsById(String id) {
        HospitalDetails hospitalDetails = hospitalDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(HospitalDetailsConstants.HOSPITAL_DETAILS_NOT_FOUND + id));
        return hospitalDetailsMapper.toDTO(hospitalDetails);
    }

    /**
     * Retrieves a list of all hospital details.
     *
     * @return A list of {@link HospitalDetailsDTO} representing all hospital details.
     */
    @Override    
    public List<HospitalDetailsDTO> getAllHospitalDetails() {
        List<HospitalDetails> hospitalDetailsList = hospitalDetailsRepository.findAll();
        return hospitalDetailsList.stream()
                .map(hospitalDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates hospital details for a specific record.
     *
     * @param id                 The ID of the hospital details to update.
     * @param hospitalDetailsDTO The updated {@link HospitalDetailsDTO}.
     * @return The updated hospital details as a {@link HospitalDetailsDTO}.
     * @throws CustomExceptions If the hospital details are not found.
     */
    @Override
    public HospitalDetailsDTO updateHospitalDetails(String id, HospitalDetailsDTO hospitalDetailsDTO) {
        HospitalDetails hospitalDetails = hospitalDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(HospitalDetailsConstants.HOSPITAL_DETAILS_NOT_FOUND + id));

        hospitalDetails.setHospitalName(hospitalDetailsDTO.getHospitalName());
        hospitalDetails.setPatientUHIDNumber(hospitalDetailsDTO.getPatientUHIDNumber());
        hospitalDetails.setConsultingDoctor(hospitalDetailsDTO.getConsultingDoctor());
        hospitalDetails.setDoctorPhoneNumber(hospitalDetailsDTO.getDoctorPhoneNumber());
        hospitalDetails.setHospitalAddress(hospitalDetailsDTO.getHospitalAddress());
        hospitalDetails.setAdditionalInformation(hospitalDetailsDTO.getAdditionalInformation());

        HospitalDetails updatedDetails = hospitalDetailsRepository.save(hospitalDetails);
        return hospitalDetailsMapper.toDTO(updatedDetails);
    }

    /**
     * Deletes hospital details by ID.
     *
     * @param id The ID of the hospital details to delete.
     */
    @Override
    public void deleteHospitalDetails(String id) {
        hospitalDetailsRepository.deleteById(id);
    }
}