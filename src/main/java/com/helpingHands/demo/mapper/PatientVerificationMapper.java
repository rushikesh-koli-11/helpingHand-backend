package com.helpingHands.demo.mapper;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.PatientVerificationDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.PatientVerification;

@Component
public class PatientVerificationMapper {

    public PatientVerificationDTO toDTO(PatientVerification entity) {
        if (entity == null) {
            return null;
        }

        PatientVerificationDTO dto = new PatientVerificationDTO();
        dto.setVerificationId(entity.getVerificationId());
        dto.setFundraiserId(entity.getFundraiser() != null ? entity.getFundraiser().getId() : null);  
        dto.setAdhaarNumber(entity.getAdhaarNumber());
        dto.setPanNumber(entity.getPanNumber());
        return dto;
    }

    public List<PatientVerificationDTO> toDTOList(List<PatientVerification> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PatientVerification toEntity(PatientVerificationDTO dto, Fundraiser fundraiser) {
        if (dto == null) {
            return null;
        }

        PatientVerification entity = new PatientVerification();
        entity.setVerificationId(dto.getVerificationId());
        entity.setFundraiser(fundraiser);  
        entity.setAdhaarNumber(dto.getAdhaarNumber());
        entity.setPanNumber(dto.getPanNumber());
        return entity;
    }
}
