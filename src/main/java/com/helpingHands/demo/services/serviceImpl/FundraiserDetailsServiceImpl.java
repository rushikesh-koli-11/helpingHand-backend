package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.FundraiserDetailsConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.globalException.Response;
import com.helpingHands.demo.mapper.FundraiserDetailsMapper;
import com.helpingHands.demo.repository.FundraiserDetailsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.FundraiserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Handling fundraiser details like creating, updating, deleting, and fetching details 
 * related to fundraisers, including cover images and patient details.
 */
@Service
public class FundraiserDetailsServiceImpl implements FundraiserDetailsService {

    @Autowired
    private FundraiserDetailsRepository fundraiserDetailsRepository;

    @Autowired
    private FundraiserRepository fundraiserRepository;

    @Autowired
    private FundraiserDetailsMapper fundraiserDetailsMapper;

    /**
     * Creating fundraiser details by storing images, videos, and patient information.
     * 
     * @param coverPicture     The cover image file.
     * @param fundraiserId     The ID of the associated fundraiser.
     * @param videoAppeal      The video appeal link.
     * @param patientName      The name of the patient.
     * @param patientAge       The age of the patient.
     * @param patientGender    The gender of the patient.
     * @param medicalCondition The medical condition of the patient.
     * @param story            The patient's story.
     * @return Response containing the created fundraiser details.
     * @throws IOException If there is an issue processing the image file.
     */
    @Override
    public Response<FundraiserDetails> createFundraiserDetails(MultipartFile coverPicture, int fundraiserId,
            String videoAppeal, String patientName, Integer patientAge, String patientGender, String medicalCondition,
            String story) throws IOException {
        // Fetching the fundraiser by ID
        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));

        // Converting image to byte array
        byte[] coverImageBytes = (coverPicture != null) ? coverPicture.getBytes() : null;

        // Creating DTO with provided details
        FundraiserDetailsDTO dto = new FundraiserDetailsDTO();
        dto.setFundraiserId(fundraiserId);
        dto.setCoverPicture(coverImageBytes);
        dto.setVideoAppeal(videoAppeal);
        dto.setRemainingAmount(fundraiser.getGoalAmount() - fundraiser.getCurrentAmount());
        dto.setPatientName(patientName);
        dto.setPatientAge(patientAge);
        dto.setPatientGender(patientGender);
        dto.setMedicalCondition(medicalCondition);
        dto.setStory(story);

        // Converting DTO to entity and saving it
        FundraiserDetails fundraiserDetails = fundraiserDetailsMapper.toEntity(dto, fundraiser);
        fundraiserDetails.setCoverPicture(coverImageBytes);

        return new Response<>(fundraiserDetailsRepository.save(fundraiserDetails));
    }

    /**
     * Fetching all fundraiser details.
     * 
     * @return A list of DTOs representing all fundraiser details.
     */
    @Override
    public List<FundraiserDetailsDTO> getAllFundraiserDetails() {
        return fundraiserDetailsRepository.findAll().stream()
                .map(fundraiserDetailsMapper::toDTO)
                .toList();
    }

    /**
     * Fetching fundraiser details by ID.
     * 
     * @param id The ID of the fundraiser details.
     * @return The DTO representation of the fundraiser details.
     * @throws CustomExceptions If fundraiser details with the given ID are not found.
     */
    @Override
    public FundraiserDetailsDTO getFundraiserDetailsById(int id) {
        FundraiserDetails entity = fundraiserDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + id));
        return fundraiserDetailsMapper.toDTO(entity);
    }

    /**
     * Deleting fundraiser details by ID.
     * 
     * @param id The ID of the fundraiser details.
     * @throws CustomExceptions If fundraiser details with the given ID are not found.
     */
    @Override
    public void deleteFundraiserDetails(int id) {
        FundraiserDetails entity = fundraiserDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + id));
        fundraiserDetailsRepository.delete(entity);
    }

    /**
     * Updating existing fundraiser details.
     * 
     * @param id  The ID of the fundraiser details.
     * @param dto The DTO containing updated values.
     * @return The updated DTO representation.
     * @throws CustomExceptions If fundraiser details with the given ID are not found.
     */
    @Override
    public FundraiserDetailsDTO updateFundraiserDetails(int id, FundraiserDetailsDTO dto) {
        // Fetching existing details
        FundraiserDetails existingDetails = fundraiserDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + id));

        // Updating details if new values are provided
        if (dto.getVideoAppeal() != null) {
            existingDetails.setVideoAppeal(dto.getVideoAppeal());
        }
        if (dto.getRemainingAmount() != null) {
            existingDetails.setRemainingAmount(dto.getRemainingAmount());
        }
        if (dto.getPatientName() != null) {
            existingDetails.setPatientName(dto.getPatientName());
        }
        if (dto.getPatientAge() != null) {
            existingDetails.setPatientAge(dto.getPatientAge());
        }
        if (dto.getPatientGender() != null) {
            existingDetails.setPatientGender(dto.getPatientGender());
        }
        if (dto.getMedicalCondition() != null) {
            existingDetails.setMedicalCondition(dto.getMedicalCondition());
        }
        if (dto.getStory() != null) {
            existingDetails.setStory(dto.getStory());
        }

        // Fetching the fundraiser to associate
        Fundraiser fundraiser = fundraiserRepository.findById(dto.getFundraiserId())
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + dto.getFundraiserId()));
        existingDetails.setFundraiser(fundraiser);

        // Saving updated details
        existingDetails = fundraiserDetailsRepository.save(existingDetails);
        return fundraiserDetailsMapper.toDTO(existingDetails);
    }

    /**
     * Fetching the cover image for a given fundraiser details ID.
     * 
     * @param id The ID of the fundraiser details.
     * @return The DTO containing the cover image.
     * @throws CustomExceptions If fundraiser details with the given ID are not found.
     */
    @Override
    public FundraiserDetailsDTO getCoverImageById(int id) {
        return fundraiserDetailsRepository.findById(id)
                .map(fundraiserDetailsMapper::toDTO)
                .orElseThrow(() -> new CustomExceptions(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND + id));
    }

    /**
     * Saving/updating the cover image for fundraiser details.
     * 
     * @param fundraiserDetailsDTO The DTO containing the cover image.
     * @throws CustomExceptions If fundraiser details with the given ID are not found.
     */
    @Override
    public void saveCoverImage(FundraiserDetailsDTO fundraiserDetailsDTO) {
        FundraiserDetails fundraiserDetails = fundraiserDetailsRepository
                .findById(fundraiserDetailsDTO.getFundraiserId())
                .orElseThrow(() -> new CustomExceptions(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND
                        + fundraiserDetailsDTO.getFundraiserId()));

        // Updating the cover image
        fundraiserDetails.setCoverPicture(fundraiserDetailsDTO.getCoverPicture());
        fundraiserDetailsRepository.save(fundraiserDetails);
    }
}
