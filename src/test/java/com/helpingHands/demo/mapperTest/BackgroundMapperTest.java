package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.*;

import com.helpingHands.demo.DTO.BackgroundDTO;
import com.helpingHands.demo.entities.Background;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.mapper.BackgroundMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BackgroundMapperTest {

    private BackgroundMapper backgroundMapper;

    @BeforeEach
    void setUp() {
        backgroundMapper = new BackgroundMapper();
    }

    @Test
    void testToDTO_ValidBackground() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        Background background = new Background();
        background.setBackgroundId(1);
        background.setFundraiser(fundraiser);
        background.setRelationWithPatient("Father");
        background.setMonthlyIncomeOfPatientsFamily(25000.0);

        BackgroundDTO dto = backgroundMapper.toDTO(background);

        assertNotNull(dto);
        assertEquals(1, dto.getBackgroundId());
        assertEquals(1, dto.getFundraiserId());
        assertEquals("Father", dto.getRelationWithPatient());
        assertEquals(25000.0, dto.getMonthlyIncomeOfPatientsFamily());
    }

    @Test
    void testToDTO_NullBackground() {
        assertNull(backgroundMapper.toDTO(null));
    }

    @Test
    void testToEntity_ValidDTO() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        BackgroundDTO dto = new BackgroundDTO();
        dto.setBackgroundId(1);
        dto.setFundraiserId(1);
        dto.setRelationWithPatient("Mother");
        dto.setMonthlyIncomeOfPatientsFamily(30000.0);

        Background background = backgroundMapper.toEntity(dto, fundraiser);

        assertNotNull(background);
        assertEquals(1, background.getBackgroundId());
        assertEquals(fundraiser, background.getFundraiser());
        assertEquals("Mother", background.getRelationWithPatient());
        assertEquals(30000.0, background.getMonthlyIncomeOfPatientsFamily());
    }

    @Test
    void testToEntity_NullDTO() {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);
        assertNull(backgroundMapper.toEntity(null, fundraiser));
    }

    @Test
    void testToEntity_NullFundraiser() {
        BackgroundDTO dto = new BackgroundDTO();
        dto.setBackgroundId(1);
        dto.setFundraiserId(1);
        dto.setRelationWithPatient("Brother");
        dto.setMonthlyIncomeOfPatientsFamily(20000.0);

        assertNull(backgroundMapper.toEntity(dto, null));
    }
}
