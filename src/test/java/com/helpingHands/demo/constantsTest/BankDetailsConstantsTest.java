package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.BankDetailsConstants;

public class BankDetailsConstantsTest {

    @Test
    void testBankDetailsConstantsValues() {
        assertNotNull(BankDetailsConstants.BANK_DETAILS_NOT_FOUND);
        assertEquals("Bank details not found with id: ", BankDetailsConstants.BANK_DETAILS_NOT_FOUND);

        assertNotNull(BankDetailsConstants.INVALID_DATA);
        assertEquals("Invalid data provided for bank details: ", BankDetailsConstants.INVALID_DATA);

        assertNotNull(BankDetailsConstants.DATABASE_ERROR);
        assertEquals("Database error occurred while processing bank details.", BankDetailsConstants.DATABASE_ERROR);

        assertNotNull(BankDetailsConstants.OPERATION_NOT_ALLOWED);
        assertEquals("Operation not allowed on the requested bank details.", BankDetailsConstants.OPERATION_NOT_ALLOWED);
    }
    
    @Test
    void testBankDetailsConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new BankDetailsConstants();
        });
    }
}

