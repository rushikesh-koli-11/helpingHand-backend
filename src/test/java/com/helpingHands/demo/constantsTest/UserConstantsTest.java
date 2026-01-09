package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.UserConstants;

public class UserConstantsTest {

    @Test
    void testUserConstantsValues() {
        assertNotNull(UserConstants.USER_NOT_FOUND);
        assertEquals("User not found!", UserConstants.USER_NOT_FOUND);

        assertNotNull(UserConstants.EMAIL_ALREADY_USED);
        assertEquals("Email is already in use!", UserConstants.EMAIL_ALREADY_USED);

        assertNotNull(UserConstants.USER_NOT_FOUND_WITH_EMAIL);
        assertEquals("User not found with email: ", UserConstants.USER_NOT_FOUND_WITH_EMAIL);

        assertNotNull(UserConstants.NO_USERS_FOUND);
        assertEquals("No users found!", UserConstants.NO_USERS_FOUND);
    }
    
    @Test
    void testUserConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new UserConstants();
        });
    }
}
