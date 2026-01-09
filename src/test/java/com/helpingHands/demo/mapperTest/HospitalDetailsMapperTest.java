package com.helpingHands.demo.mapperTest;

import com.helpingHands.demo.DTO.HospitalDetailsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.HospitalDetails;
import com.helpingHands.demo.mapper.HospitalDetailsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalDetailsMapperTest {

    private HospitalDetailsMapper hospitalDetailsMapper;

    @BeforeEach
    void setUp() {
        hospitalDetailsMapper = new HospitalDetailsMapper();
    }

    @Test
    void testToEntity_ValidDTO() {
        HospitalDetailsDTO dto = HospitalDetailsDTO.builder()
                .id(1)
                .fundraiserId(1)
                .hospitalName("City Hospital")
                .patientUHIDNumber(12345678901234L)
                .consultingDoctor("Dr. Mohan")
                .doctorPhoneNumber(9876543210L)
                .hospitalAddress("123, Church Street")
                .additionalInformation("Emergency case")
                .build();

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(dto.getFundraiserId());

        HospitalDetails entity = hospitalDetailsMapper.toEntity(dto, fundraiser);

        assertNotNull(entity);
        assertEquals(dto.getHospitalName(), entity.getHospitalName());
        assertEquals(dto.getPatientUHIDNumber(), entity.getPatientUHIDNumber());
        assertEquals(dto.getConsultingDoctor(), entity.getConsultingDoctor());
        assertEquals(dto.getDoctorPhoneNumber(), entity.getDoctorPhoneNumber());
        assertEquals(dto.getHospitalAddress(), entity.getHospitalAddress());
        assertEquals(dto.getAdditionalInformation(), entity.getAdditionalInformation());
        assertEquals(dto.getFundraiserId(), entity.getFundraiser().getId());
    }

    @Test
    void testToEntity_NullDTO() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);
        assertNull(hospitalDetailsMapper.toEntity(null, fundraiser));
    }

    @Test
    void testToDTO_ValidEntity() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        HospitalDetails entity = new HospitalDetails();
        entity.setId(1);
        entity.setFundraiser(fundraiser);
        entity.setHospitalName("City Hospital");
        entity.setPatientUHIDNumber(12345678901234L);
        entity.setConsultingDoctor("Dr. Mohan");
        entity.setDoctorPhoneNumber(9876543210L);
        entity.setHospitalAddress("123, Church Street");
        entity.setAdditionalInformation("Emergency case");

        HospitalDetailsDTO dto = hospitalDetailsMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getHospitalName(), dto.getHospitalName());
        assertEquals(entity.getPatientUHIDNumber(), dto.getPatientUHIDNumber());
        assertEquals(entity.getConsultingDoctor(), dto.getConsultingDoctor());
        assertEquals(entity.getDoctorPhoneNumber(), dto.getDoctorPhoneNumber());
        assertEquals(entity.getHospitalAddress(), dto.getHospitalAddress());
        assertEquals(entity.getAdditionalInformation(), dto.getAdditionalInformation());
        assertEquals(entity.getFundraiser().getId(), dto.getFundraiserId());
    }

    @Test
    void testToDTO_NullEntity() {
        assertNull(hospitalDetailsMapper.toDTO(null));
    }
}
