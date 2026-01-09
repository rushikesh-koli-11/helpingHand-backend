package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.PatientVerificationConstants;

public class PatientVerificationConstantsTest {

    @Test
    void testPatientVerificationConstantsValue() {
        assertNotNull(PatientVerificationConstants.PATIENT_VERIFICATION_NOT_FOUND);
        assertEquals("PatientVerification not found with id: ", PatientVerificationConstants.PATIENT_VERIFICATION_NOT_FOUND);
    }
    
    @Test
    void testPatientVerificationConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new PatientVerificationConstants();
        });
    }
}
