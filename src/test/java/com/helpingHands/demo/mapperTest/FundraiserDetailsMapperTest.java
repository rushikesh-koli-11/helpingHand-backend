package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.mapper.FundraiserDetailsMapper;

public class FundraiserDetailsMapperTest {

    @InjectMocks
    private FundraiserDetailsMapper fundraiserDetailsMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDTO_NullEntity() {
        assertNull(fundraiserDetailsMapper.toDTO(null));
    }

    @Test
    void testToDTO_ValidEntity() {
        // Given
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        FundraiserDetails entity = new FundraiserDetails();
        entity.setId(1);
        entity.setFundraiser(fundraiser);
        entity.setCoverPicture(new byte[]{1, 2, 3});
        entity.setVideoAppeal("www.youtube.com");
        entity.setRemainingAmount(5000.0);
        entity.setPatientName("Varun Kulkarni");
        entity.setPatientAge(30);
        entity.setPatientGender("Male");
        entity.setMedicalCondition("Heart Disease");
        entity.setStory("This is a sample story");

        // When
        FundraiserDetailsDTO dto = fundraiserDetailsMapper.toDTO(entity);

        // Then
        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals(1, dto.getFundraiserId());
        assertArrayEquals(new byte[]{1, 2, 3}, dto.getCoverPicture());
        assertEquals("www.youtube.com", dto.getVideoAppeal());
        assertEquals(5000.0, dto.getRemainingAmount());
        assertEquals("Varun Kulkarni", dto.getPatientName());
        assertEquals(30, dto.getPatientAge());
        assertEquals("Male", dto.getPatientGender());
        assertEquals("Heart Disease", dto.getMedicalCondition());
        assertEquals("This is a sample story", dto.getStory());
    }

    @Test
    void testToEntity_NullDTO() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);
        assertNull(fundraiserDetailsMapper.toEntity(null, fundraiser));
    }

    @Test
    void testToEntity_ValidDTO() {
        // Given
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        FundraiserDetailsDTO dto = FundraiserDetailsDTO.builder()
                .id(1)
                .fundraiserId(1)
                .coverPicture(new byte[]{1, 2, 3})
                .videoAppeal("www.youtube.com")
                .remainingAmount(5000.0)
                .patientName("Varun Kulkarni")
                .patientAge(30)
                .patientGender("Male")
                .medicalCondition("Heart Disease")
                .story("This is a sample story")
                .build();

        // When
        FundraiserDetails entity = fundraiserDetailsMapper.toEntity(dto, fundraiser);

        // Then
        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals(1, entity.getFundraiser().getId());
        assertArrayEquals(new byte[]{1, 2, 3}, entity.getCoverPicture());
        assertEquals("www.youtube.com", entity.getVideoAppeal());
        assertEquals(5000.0, entity.getRemainingAmount());
        assertEquals("Varun Kulkarni", entity.getPatientName());
        assertEquals(30, entity.getPatientAge());
        assertEquals("Male", entity.getPatientGender());
        assertEquals("Heart Disease", entity.getMedicalCondition());
        assertEquals("This is a sample story", entity.getStory());
    }
}
