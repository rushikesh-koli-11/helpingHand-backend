package com.helpingHands.demo.services.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpingHands.demo.DTO.FundraiserDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.FundraiserMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.FundraiserService;

/**
 * Service implementation for managing fundraisers.
 * This class is handling all the operations related to fundraisers like creating, retrieving, 
 * updating, and deleting. Also, it is handling approval status updates and sending emails 
 * based on the fundraiser status.
 */
@Service
public class FundraiserServiceImpl implements FundraiserService {

    @Autowired
    private FundraiserRepository fundraiserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundraiserMapper fundraiserMapper;

    @Autowired
    private EmailService emailService;

    /**
     * Creating a new fundraiser based on the provided FundraiserDTO.
     * 
     * @param fundraiserDTO The DTO containing fundraiser details.
     * @return The DTO representation of the created fundraiser.
     * @throws CustomExceptions If the user associated with the fundraiser is not found.
     */
    @Override
    public FundraiserDTO createFundraiser(FundraiserDTO fundraiserDTO) {
        // Fetching the user by ID to associate with the fundraiser.
        User user = userRepository.findById(fundraiserDTO.getUserId()).orElseThrow(
                () -> new CustomExceptions(FundraiserConstants.USER_NOT_FOUND + fundraiserDTO.getUserId()));

        // Converting DTO to entity and saving it.
        Fundraiser fundraiser = fundraiserMapper.toEntity(fundraiserDTO, user);
        fundraiser = fundraiserRepository.save(fundraiser);

        // Returning the DTO representation of the saved fundraiser.
        return fundraiserMapper.toDTO(fundraiser);
    }

    /**
     * Fetching all fundraisers.
     * 
     * @return A list of DTOs representing all fundraisers.
     */
    @Override
    public List<FundraiserDTO> getAllFundraisers() {
        return fundraiserRepository.findAll().stream()
                .map(fundraiserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Fetching all fundraisers except the ones created by the given user ID.
     * 
     * @param userId The user ID to exclude from the list.
     * @return A list of DTOs representing all fundraisers except the given user's.
     */
    @Override
    public List<FundraiserDTO> getAllFundraisersExceptUserId(Integer userId) {
        return fundraiserRepository.findAll().stream()
                .filter(fundraiser -> fundraiser.getUser().getUserId() != userId)
                .map(fundraiserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Fetching a fundraiser by its ID.
     * 
     * @param fundraiserId The ID of the fundraiser.
     * @return The DTO representation of the fundraiser.
     * @throws CustomExceptions If the fundraiser with the given ID is not found.
     */
    @Override
    public FundraiserDTO getFundraiserById(int fundraiserId) {
        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));
        return fundraiserMapper.toDTO(fundraiser);
    }

    /**
     * Fetching the latest fundraiser (the one with the highest ID).
     * 
     * @return The DTO representation of the latest fundraiser.
     * @throws CustomExceptions If no fundraisers are found.
     */
    @Override
    public FundraiserDTO getLatestFundraiser() {
        Fundraiser latestFundraiser = fundraiserRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.NO_FUNDRAISERS_FOUND));
        return fundraiserMapper.toDTO(latestFundraiser);
    }

    /**
     * Deleting a fundraiser by its ID.
     * 
     * @param fundraiserId The ID of the fundraiser.
     * @throws CustomExceptions If the fundraiser with the given ID does not exist.
     */
    @Override
    public void deleteFundraiser(int fundraiserId) {
        if (!fundraiserRepository.existsById(fundraiserId)) {
            throw new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId);
        }
        fundraiserRepository.deleteById(fundraiserId);
    }

    /**
     * Updating the approval status of a fundraiser and sending an email to the user
     * based on the status of the fundraiser (approved or not).
     * 
     * @param fundraiserId The ID of the fundraiser.
     * @param status The new status of the fundraiser ("approved" or "not approved").
     * @throws CustomExceptions If the fundraiser with the given ID is not found.
     */
    @Override
    public void updateApprovalStatus(int fundraiserId, String status) {
        // Fetching the fundraiser by ID.
        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));

        // Updating the fundraiser status.
        fundraiser.setStatus(status);

        // Sending an email based on the approval status.
        if ("approved".equals(fundraiser.getStatus())) {
            emailService.sendEmail(fundraiser.getUser().getEmail(),
                                  "📢 Fundraiser Approved!",
                                  "Dear " + fundraiser.getUser().getName() + ",\r\n"
                                  + "\r\n"
                                  + "We’re excited to inform you that your fund has been approved! 🎉 Your campaign is now live and ready to receive contributions.\r\n"
                                  + "\r\n"
                                  + "Start sharing your fundraiser with friends, family, and supporters to make the biggest impact. 🚀\r\n"
                                  + "\r\n"
                                  + "Thank you for being a part of our mission!\r\n"
                                  + "\r\n"
                                  + "Best,\r\n"
                                  + "Helping Hands");
        } else {
            emailService.sendEmail(fundraiser.getUser().getEmail(),
                                  "⚠ Fundraiser Not Approved\r\n",
                                  "Dear " + fundraiser.getUser().getName() + ",\r\n"
                                  + "\r\n"
                                  + "Thank you for submitting your fund. After careful review, we regret to inform you that we are unable to approve your fundraiser at this time due to missing details and policy guidelines.\r\n"
                                  + "\r\n"
                                  + "We truly appreciate your efforts and encourage you to review our guidelines and resubmit with the necessary changes. If you have any questions, feel free to reach out to us.\r\n"
                                  + "\r\n"
                                  + "We apologize for any inconvenience and appreciate your understanding.\r\n"
                                  + "\r\n"
                                  + "Best,\r\n"
                                  + "Helping Hands");
        }

        // Saving the updated fundraiser.
        fundraiserRepository.save(fundraiser);
    }
}
