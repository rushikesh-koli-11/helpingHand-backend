package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.DonationConstants;

public class DonationConstantsTest {

    @Test
    void testDonationConstantsValues() {
        assertNotNull(DonationConstants.DONATION_NOT_FOUND);
        assertEquals("Donation not found with ID: ", DonationConstants.DONATION_NOT_FOUND);

        assertNotNull(DonationConstants.INVALID_DONATION_DATA);
        assertEquals("Invalid donation data provided.", DonationConstants.INVALID_DONATION_DATA);

        assertNotNull(DonationConstants.DATABASE_ERROR);
        assertEquals("Database error occurred while processing the donation.", DonationConstants.DATABASE_ERROR);
    }
    
    @Test
    void testDonationConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new DonationConstants();
        });
    }
}
