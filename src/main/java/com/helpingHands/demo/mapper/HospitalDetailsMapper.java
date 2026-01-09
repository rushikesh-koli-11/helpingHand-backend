package com.helpingHands.demo.mapper;

import org.springframework.stereotype.Component;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.HospitalDetails;

@Component
public class HospitalDetailsMapper {

	public HospitalDetails toEntity(HospitalDetailsDTO dto, Fundraiser fundraiser) {
	    if (dto == null) {
	        return null;
	    }

	    HospitalDetails entity = new HospitalDetails();
	    entity.setHospitalName(dto.getHospitalName());
	    entity.setPatientUHIDNumber(dto.getPatientUHIDNumber());
	    entity.setConsultingDoctor(dto.getConsultingDoctor());
	    entity.setDoctorPhoneNumber(dto.getDoctorPhoneNumber());
	    entity.setHospitalAddress(dto.getHospitalAddress());
	    entity.setAdditionalInformation(dto.getAdditionalInformation());
	    entity.setFundraiser(fundraiser);
	    return entity;
	}


    public HospitalDetailsDTO toDTO(HospitalDetails entity) {
        if (entity == null) {
            return null;
        }

        HospitalDetailsDTO dto = new HospitalDetailsDTO();
        dto.setId(entity.getId());
        dto.setHospitalName(entity.getHospitalName());
        dto.setPatientUHIDNumber(entity.getPatientUHIDNumber());
        dto.setConsultingDoctor(entity.getConsultingDoctor());
        dto.setDoctorPhoneNumber(entity.getDoctorPhoneNumber());
        dto.setHospitalAddress(entity.getHospitalAddress());
        dto.setAdditionalInformation(entity.getAdditionalInformation());
        dto.setFundraiserId(entity.getFundraiser() != null ? entity.getFundraiser().getId() : null); // Handle potential null fundraiser
        return dto;
    }

}
