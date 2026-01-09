package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.PatientVerificationDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.constants.PatientVerificationConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.PatientVerification;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.PatientVerificationMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.PatientVerificationRepository;
import com.helpingHands.demo.services.PatientVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link PatientVerificationService} interface.
 * This class provides methods to manage patient verification records,
 * including creating, retrieving, updating, and deleting patient verifications.
 */
@Service
public class PatientVerificationServiceImpl implements PatientVerificationService {

    @Autowired
    private PatientVerificationRepository patientVerificationRepository;

    @Autowired
    private PatientVerificationMapper patientVerificationMapper;

    @Autowired
    private FundraiserRepository fundraiserRepository;

    /**
     * Creates a new patient verification record.
     *
     * @param dto The {@link PatientVerificationDTO} containing the patient verification details.
     * @return The created patient verification as a {@link PatientVerificationDTO}.
     * @throws CustomExceptions If the associated fundraiser is not found.
     */
    @Override
    public PatientVerificationDTO createPatientVerification(PatientVerificationDTO dto) {
        Fundraiser fundraiser = fundraiserRepository.findById(dto.getFundraiserId())
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + dto.getFundraiserId()));

        PatientVerification entity = patientVerificationMapper.toEntity(dto, fundraiser);
        entity = patientVerificationRepository.save(entity);
        return patientVerificationMapper.toDTO(entity);
    }

    /**
     * Retrieves all patient verification records.
     *
     * @return A list of {@link PatientVerificationDTO} objects representing all patient verifications.
     */
    @Override
    public List<PatientVerificationDTO> getAllPatientVerifications() {
        List<PatientVerification> patientVerifications = patientVerificationRepository.findAll();
        return patientVerificationMapper.toDTOList(patientVerifications);
    }

    /**
     * Retrieves a patient verification record by the associated fundraiser ID.
     *
     * @param fundraiserId The ID of the fundraiser associated with the patient verification.
     * @return The {@link PatientVerificationDTO} representing the patient verification.
     * @throws CustomExceptions If the fundraiser is not found.
     */
    @Override
    public PatientVerificationDTO getPatientVerificationByfundraiserId(int fundraiserId) {
        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));

        return patientVerificationMapper.toDTO(fundraiser.getPatientVerification());
    }

    /**
     * Deletes a patient verification record by its ID.
     *
     * @param id The ID of the patient verification to delete.
     */
    @Override
    public void deletePatientVerification(int id) {
        patientVerificationRepository.deleteById(id);
    }

    /**
     * Updates an existing patient verification record.
     *
     * @param id  The ID of the patient verification to update.
     * @param dto The {@link PatientVerificationDTO} containing the updated details.
     * @return The updated patient verification as a {@link PatientVerificationDTO}.
     * @throws CustomExceptions If the patient verification or associated fundraiser is not found.
     */
    @Override
    public PatientVerificationDTO updatePatientVerification(int id, PatientVerificationDTO dto) {
        PatientVerification existingPatientVerification = patientVerificationRepository
                .findById(id)
                .orElseThrow(() -> new CustomExceptions(PatientVerificationConstants.PATIENT_VERIFICATION_NOT_FOUND + id));

        existingPatientVerification.setAdhaarNumber(dto.getAdhaarNumber());
        existingPatientVerification.setPanNumber(dto.getPanNumber());
        // No longer need to set the fundraiser directly because it's already linked

        PatientVerification updatedPatientVerification = patientVerificationRepository.save(existingPatientVerification);

        return patientVerificationMapper.toDTO(updatedPatientVerification);
    }
}