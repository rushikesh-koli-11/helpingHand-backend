package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.constants.MedicalDocumentsConstants;
import com.helpingHands.demo.entities.MedicalDocuments;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.MedicalDocumentsMapper;
import com.helpingHands.demo.repository.MedicalDocumentsRepository;
import com.helpingHands.demo.services.CloudinaryService;
import com.helpingHands.demo.services.MedicalDocumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Service implementation for managing medical documents.
 * This class provides functionality for uploading, retrieving, and updating medical documents
 * associated with fundraisers using Cloudinary for file storage.
 */
@Service
@RequiredArgsConstructor
public class MedicalDocumentsServiceImpl implements MedicalDocumentsService {

    private final MedicalDocumentsRepository medicalDocumentsRepository;
    private final MedicalDocumentsMapper medicalDocumentsMapper;
    private final CloudinaryService cloudinaryService;

    /**
     * Uploads medical documents by uploading them to Cloudinary and saving URLs in the database.
     *
     * @param dto The {@link MedicalDocumentsDTO} containing the documents to upload.
     * @return The uploaded documents as a {@link MedicalDocumentsDTO}.
     * @throws CustomExceptions If there is an error during file upload.
     */
    @Override
    public MedicalDocumentsDTO uploadDocuments(MedicalDocumentsDTO dto) {
        // Note: DTO should already contain Cloudinary URLs if files were uploaded via controller
        // Mapping the DTO to an entity and saving it in the database
        MedicalDocuments medicalDocuments = medicalDocumentsMapper.toEntity(dto);
        medicalDocuments = medicalDocumentsRepository.save(medicalDocuments);

        // Returning the saved entity as a DTO
        return medicalDocumentsMapper.toDTO(medicalDocuments);
    }

    /**
     * Retrieves medical documents by the associated fundraiser ID.
     *
     * @param fundraiserId The ID of the fundraiser associated with the medical documents.
     * @return An {@link Optional} containing the {@link MedicalDocumentsDTO} if found, otherwise empty.
     */
    @Override
    public Optional<MedicalDocumentsDTO> getMedicalDocumentsByFundraiserId(String fundraiserId) {
        return medicalDocumentsRepository.findByFundraiserId(fundraiserId).map(medicalDocumentsMapper::toDTO);
    }

    /**
     * Updates medical documents for a specific fundraiser.
     *
     * @param fundraiserId              The ID of the fundraiser associated with the documents.
     * @param medicalEstimate           The updated medical estimate file.
     * @param consentLetterFromPatient  The updated consent letter file.
     * @param medicalReports            The updated medical reports file.
     * @param otherDocs                 The updated other documents file.
     * @param additionalInformation     The updated additional information.
     * @return The updated documents as a {@link MedicalDocumentsDTO}.
     * @throws CustomExceptions If the documents are not found or there is an error updating the files.
     */
    @Override
    public MedicalDocumentsDTO updateDocuments(String fundraiserId, MultipartFile medicalEstimate,
                                              MultipartFile consentLetterFromPatient, MultipartFile medicalReports,
                                              MultipartFile otherDocs, String additionalInformation) {
        // Retrieving the existing medical documents entity
        MedicalDocuments entity = medicalDocumentsRepository.findByFundraiserId(fundraiserId).orElseThrow(
                () -> new CustomExceptions(MedicalDocumentsConstants.MEDICAL_DOCUMENTS_NOT_FOUND + fundraiserId));

        try {
            // Uploading files to Cloudinary if they are not null
            if (medicalEstimate != null && !medicalEstimate.isEmpty()) {
                String url = cloudinaryService.uploadFile(medicalEstimate, "medical-documents");
                entity.setMedicalEstimate(url);
            }
            if (consentLetterFromPatient != null && !consentLetterFromPatient.isEmpty()) {
                String url = cloudinaryService.uploadFile(consentLetterFromPatient, "medical-documents");
                entity.setConsentLetterFromPatient(url);
            }
            if (medicalReports != null && !medicalReports.isEmpty()) {
                String url = cloudinaryService.uploadFile(medicalReports, "medical-documents");
                entity.setMedicalReports(url);
            }
            if (otherDocs != null && !otherDocs.isEmpty()) {
                String url = cloudinaryService.uploadFile(otherDocs, "medical-documents");
                entity.setOtherDocs(url);
            }
        } catch (IOException e) {
            // Throwing an exception if there is an error updating the files
            throw new CustomExceptions(MedicalDocumentsConstants.FILE_UPDATE_ERROR);
        }

        // Updating additional information if it is not null
        if (additionalInformation != null) {
            entity.setAdditionalInformation(additionalInformation);
        }

        // Saving the updated entity and returning it as a DTO
        entity = medicalDocumentsRepository.save(entity);
        return medicalDocumentsMapper.toDTO(entity);
    }
}