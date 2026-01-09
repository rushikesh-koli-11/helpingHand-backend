package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.*;

import com.helpingHands.demo.DTO.SavedFundDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.SavedFund;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.SavedFundMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavedFundMapperTest {

    private SavedFundMapper savedFundMapper;

    @BeforeEach
    void setUp() {
        savedFundMapper = new SavedFundMapper();
    }

    @Test
    void testToDTO() {
        User user = new User();
        user.setUserId(1);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(2);

        SavedFund savedFund = new SavedFund();
        savedFund.setSaveId(1);
        savedFund.setUser(user);
        savedFund.setFundraiser(fundraiser);

        SavedFundDTO savedFundDTO = savedFundMapper.toDTO(savedFund);

        assertNotNull(savedFundDTO);
        assertEquals(1, savedFundDTO.getSaveId());
        assertEquals(1, savedFundDTO.getUserId());
        assertEquals(2, savedFundDTO.getFundraiserId());
    }

    @Test
    void testToEntity() {
        SavedFundDTO savedFundDTO = SavedFundDTO.builder()
                .saveId(1)
                .userId(1)
                .fundraiserId(2)
                .build();

        User user = new User();
        user.setUserId(1);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(2);

        SavedFund savedFund = savedFundMapper.toEntity(savedFundDTO, user, fundraiser);

        assertNotNull(savedFund);
        assertEquals(1, savedFund.getSaveId());
        assertEquals(1, savedFund.getUser().getUserId());
        assertEquals(2, savedFund.getFundraiser().getId());
    }
}
