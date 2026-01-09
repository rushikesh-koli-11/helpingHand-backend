package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.helpingHands.demo.DTO.FundraiserDTO;
import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.FundraiserDetailsMapper;
import com.helpingHands.demo.mapper.FundraiserMapper;

public class FundraiserMapperTest {

    private FundraiserMapper fundraiserMapper;

    @Mock
    private FundraiserDetailsMapper fundraiserDetailsMapper;

    @BeforeEach
    void setUp() {
        fundraiserDetailsMapper = Mockito.mock(FundraiserDetailsMapper.class);
        fundraiserMapper = new FundraiserMapper();
        fundraiserMapper.fundraiserDetailsMapper = fundraiserDetailsMapper;
    }

    @Test
    void testToEntity_Success() {
        User user = new User();
        user.setUserId(1);

        FundraiserDTO dto = FundraiserDTO.builder()
                .fundraiserId(1)
                .userId(1)
                .title("Medical Aid")
                .description("Need funds for surgery")
                .goalAmount(50000.0)
                .currentAmount(10000.0)
                .status("approved")
                .mobileNumber(9876543210L)
                .build();

        Fundraiser fundraiser = fundraiserMapper.toEntity(dto, user);

        assertNotNull(fundraiser);
        assertEquals(1, fundraiser.getId());
        assertEquals("Medical Aid", fundraiser.getTitle());
        assertEquals("Need funds for surgery", fundraiser.getDescription());
        assertEquals(50000.0, fundraiser.getGoalAmount());
        assertEquals(10000.0, fundraiser.getCurrentAmount());
        assertEquals("approved", fundraiser.getStatus());
        assertEquals(9876543210L, fundraiser.getMobileNumber());
        assertEquals(1, fundraiser.getUser().getUserId());
    }

    @Test
    void testToEntity_NullDTO() {
        User user = new User();
        user.setUserId(1);
        assertNull(fundraiserMapper.toEntity(null, user));
    }

    @Test
    void testToEntity_NullUser() {
        FundraiserDTO dto = FundraiserDTO.builder()
                .fundraiserId(1)
                .title("Medical Aid")
                .description("Need funds for surgery")
                .goalAmount(50000.0)
                .currentAmount(10000.0)
                .status("approved")
                .mobileNumber(9876543210L)
                .build();

        Fundraiser fundraiser = fundraiserMapper.toEntity(dto, null);
        assertNotNull(fundraiser);
        assertNull(fundraiser.getUser());
    }

    @Test
    void testToDTO_Success() {
        User user = new User();
        user.setUserId(1);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setTitle("Medical Aid");
        fundraiser.setDescription("Need funds for surgery");
        fundraiser.setGoalAmount(50000.0);
        fundraiser.setCurrentAmount(10000.0);
        fundraiser.setStatus("approved");
        fundraiser.setMobileNumber(9876543210L);
        fundraiser.setUser(user);

        FundraiserDetails fundraiserDetails = new FundraiserDetails();
        fundraiser.setFundraiserDetails(fundraiserDetails);

        FundraiserDetailsDTO fundraiserDetailsDTO = new FundraiserDetailsDTO();
        Mockito.when(fundraiserDetailsMapper.toDTO(fundraiserDetails)).thenReturn(fundraiserDetailsDTO);

        FundraiserDTO dto = fundraiserMapper.toDTO(fundraiser);

        assertNotNull(dto);
        assertEquals(1, dto.getFundraiserId());
        assertEquals("Medical Aid", dto.getTitle());
        assertEquals("Need funds for surgery", dto.getDescription());
        assertEquals(50000.0, dto.getGoalAmount());
        assertEquals(10000.0, dto.getCurrentAmount());
        assertEquals("approved", dto.getStatus());
        assertEquals(9876543210L, dto.getMobileNumber());
        assertEquals(1, dto.getUserId());
        assertNotNull(dto.getFundraiserDetailsDTO());
    }

    @Test
    void testToDTO_NullFundraiser() {
        assertNull(fundraiserMapper.toDTO(null));
    }

    @Test
    void testToDTO_FundraiserWithoutDetails() {
        User user = new User();
        user.setUserId(1);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);
        fundraiser.setTitle("Medical Aid");
        fundraiser.setDescription("Need funds for surgery");
        fundraiser.setGoalAmount(50000.0);
        fundraiser.setCurrentAmount(10000.0);
        fundraiser.setStatus("approved");
        fundraiser.setMobileNumber(9876543210L);
        fundraiser.setUser(user);
        fundraiser.setFundraiserDetails(null);

        FundraiserDTO dto = fundraiserMapper.toDTO(fundraiser);

        assertNotNull(dto);
        assertNull(dto.getFundraiserDetailsDTO());
    }
}
