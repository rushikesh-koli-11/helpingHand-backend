package com.helpingHands.demo.mapperTest;

import com.helpingHands.demo.DTO.PatientVerificationDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.PatientVerification;
import com.helpingHands.demo.mapper.PatientVerificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatientVerificationMapperTest {

    private PatientVerificationMapper patientVerificationMapper;

    @BeforeEach
    void setUp() {
        patientVerificationMapper = new PatientVerificationMapper();
    }

    @Test
    void testToDTO() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        PatientVerification patientVerification = new PatientVerification();
        patientVerification.setVerificationId(1);
        patientVerification.setFundraiserId(fundraiser);
        patientVerification.setAdhaarNumber(123456789012L);
        patientVerification.setPanNumber("ABCDE1234F");

        PatientVerificationDTO dto = patientVerificationMapper.toDTO(patientVerification);

        assertNotNull(dto);
        assertEquals(1, dto.getVerificationId());
        assertEquals(1, dto.getFundraiserId());
        assertEquals(123456789012L, dto.getAdhaarNumber());
        assertEquals("ABCDE1234F", dto.getPanNumber());
    }

    @Test
    void testToDTO_NullEntity() {
        assertNull(patientVerificationMapper.toDTO(null));
    }

    @Test
    void testToDTOList() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        PatientVerification pv1 = new PatientVerification();
        pv1.setVerificationId(101);
        pv1.setFundraiserId(fundraiser);
        pv1.setAdhaarNumber(111111111111L);
        pv1.setPanNumber("AAAAA1234A");

        PatientVerification pv2 = new PatientVerification();
        pv2.setVerificationId(102);
        pv2.setFundraiserId(fundraiser);
        pv2.setAdhaarNumber(222222222222L);
        pv2.setPanNumber("BBBBB1234B");

        List<PatientVerification> entities = Arrays.asList(pv1, pv2);

        List<PatientVerificationDTO> dtos = patientVerificationMapper.toDTOList(entities);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(101, dtos.get(0).getVerificationId());
        assertEquals(111111111111L, dtos.get(0).getAdhaarNumber());
        assertEquals("AAAAA1234A", dtos.get(0).getPanNumber());

        assertEquals(102, dtos.get(1).getVerificationId());
        assertEquals(222222222222L, dtos.get(1).getAdhaarNumber());
        assertEquals("BBBBB1234B", dtos.get(1).getPanNumber());
    }

    @Test
    void testToDTOList_NullOrEmpty() {
        assertNull(patientVerificationMapper.toDTOList(null));
        assertNull(patientVerificationMapper.toDTOList(List.of()));
    }

    @Test
    void testToEntity() {
        PatientVerificationDTO dto = PatientVerificationDTO.builder()
                .verificationId(1)
                .fundraiserId(1)
                .adhaarNumber(123456789012L)
                .panNumber("ABCDE1234F")
                .build();

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        PatientVerification entity = patientVerificationMapper.toEntity(dto, fundraiser);

        assertNotNull(entity);
        assertEquals(1, entity.getVerificationId());
        assertEquals(1, entity.getFundraiserId().getId());
        assertEquals(123456789012L, entity.getAdhaarNumber());
        assertEquals("ABCDE1234F", entity.getPanNumber());
    }

    @Test
    void testToEntity_NullDTO() {
        assertNull(patientVerificationMapper.toEntity(null, new Fundraiser()));
    }
}
