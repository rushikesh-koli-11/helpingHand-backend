package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.FundraiserDetailsConstants;

public class FundraiserDetailsConstantsTest {

    @Test
    void testFundraiserDetailsConstantsValues() {
        assertNotNull(FundraiserDetailsConstants.USER_NOT_FOUND);
        assertEquals("User not found with ID: ", FundraiserDetailsConstants.USER_NOT_FOUND);

        assertNotNull(FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND);
        assertEquals("Fundraiser details not found with ID: ", FundraiserDetailsConstants.FUNDRAISER_DETAILS_NOT_FOUND);

        assertNotNull(FundraiserDetailsConstants.NO_FUNDRAISERS_FOUND);
        assertEquals("No fundraisers found!", FundraiserDetailsConstants.NO_FUNDRAISERS_FOUND);
    }
    
    @Test
    void testFundraiserDetailsConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new FundraiserDetailsConstants();
        });
    }

}

