package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.MedicalDocumentsDTO;
import com.helpingHands.demo.constants.MedicalDocumentsConstants;
import com.helpingHands.demo.entities.MedicalDocuments;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.MedicalDocumentsMapper;
import com.helpingHands.demo.repository.MedicalDocumentsRepository;
import com.helpingHands.demo.services.MedicalDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Service implementation for managing medical documents.
 * This class provides functionality for uploading, retrieving, and updating medical documents
 * associated with fundraisers.
 */
@Service
public class MedicalDocumentsServiceImpl implements MedicalDocumentsService {

    @Autowired
    private MedicalDocumentsRepository medicalDocumentsRepository;

    @Autowired
    private MedicalDocumentsMapper medicalDocumentsMapper;

    /**
     * Uploads medical documents by converting them to byte arrays and saving them in the database.
     *
     * @param dto The {@link MedicalDocumentsDTO} containing the documents to upload.
     * @return The uploaded documents as a {@link MedicalDocumentsDTO}.
     * @throws CustomExceptions If there is an error during file conversion.
     */
    @Override
    public MedicalDocumentsDTO uploadDocuments(MedicalDocumentsDTO dto) {
        try {
            // Converting files to byte arrays if they are not null
            if (dto.getMedicalEstimate() != null) {
                dto.setMedicalEstimate(convertFileToByteArray(dto.getMedicalEstimate()));
            }
            if (dto.getConsentLetterFromPatient() != null) {
                dto.setConsentLetterFromPatient(convertFileToByteArray(dto.getConsentLetterFromPatient()));
            }
            if (dto.getMedicalReports() != null) {
                dto.setMedicalReports(convertFileToByteArray(dto.getMedicalReports()));
            }
            if (dto.getOtherDocs() != null) {
                dto.setOtherDocs(convertFileToByteArray(dto.getOtherDocs()));
            }
        } catch (Exception e) {
            // Throwing an exception if file conversion fails
            throw new CustomExceptions(MedicalDocumentsConstants.FILE_CONVERSION_ERROR);
        }

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
    public Optional<MedicalDocumentsDTO> getMedicalDocumentsByFundraiserId(int fundraiserId) {
        return medicalDocumentsRepository.findByFundraiser_Id(fundraiserId).map(medicalDocumentsMapper::toDTO);
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
    public MedicalDocumentsDTO updateDocuments(int fundraiserId, MultipartFile medicalEstimate,
                                              MultipartFile consentLetterFromPatient, MultipartFile medicalReports,
                                              MultipartFile otherDocs, String additionalInformation) {
        // Retrieving the existing medical documents entity
        MedicalDocuments entity = medicalDocumentsRepository.findByFundraiser_Id(fundraiserId).orElseThrow(
                () -> new CustomExceptions(MedicalDocumentsConstants.MEDICAL_DOCUMENTS_NOT_FOUND + fundraiserId));

        try {
            // Updating the files if they are not null
            if (medicalEstimate != null)
                entity.setMedicalEstimate(medicalEstimate.getBytes());
            if (consentLetterFromPatient != null)
                entity.setConsentLetterFromPatient(consentLetterFromPatient.getBytes());
            if (medicalReports != null)
                entity.setMedicalReports(medicalReports.getBytes());
            if (otherDocs != null)
                entity.setOtherDocs(otherDocs.getBytes());
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

    /**
     * Converts a file to a byte array.
     *
     * @param file The file to convert.
     * @return The file as a byte array.
     */
    private byte[] convertFileToByteArray(byte[] file) {
        return file;
    }
}