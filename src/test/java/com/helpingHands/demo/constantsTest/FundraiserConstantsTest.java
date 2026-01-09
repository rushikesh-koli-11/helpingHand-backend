package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.FundraiserConstants;

public class FundraiserConstantsTest {

    @Test
    void testFundraiserConstantsValues() {
        assertNotNull(FundraiserConstants.USER_NOT_FOUND);
        assertEquals("User not found with ID: ", FundraiserConstants.USER_NOT_FOUND);

        assertNotNull(FundraiserConstants.FUNDRAISER_NOT_FOUND);
        assertEquals("Fundraiser not found with ID: ", FundraiserConstants.FUNDRAISER_NOT_FOUND);

        assertNotNull(FundraiserConstants.NO_FUNDRAISERS_FOUND);
        assertEquals("No fundraisers found!", FundraiserConstants.NO_FUNDRAISERS_FOUND);
    }
    
    @Test
    void testFundraiserConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new FundraiserConstants();
        });
    }
}

