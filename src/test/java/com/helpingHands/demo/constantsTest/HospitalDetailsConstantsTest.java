package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.HospitalDetailsConstants;

public class HospitalDetailsConstantsTest {

    @Test
    void testHospitalDetailsConstantsValues() {
        assertNotNull(HospitalDetailsConstants.HOSPITAL_DETAILS_NOT_FOUND);
        assertEquals("Hospital details not found with id: ", HospitalDetailsConstants.HOSPITAL_DETAILS_NOT_FOUND);
    }
    
    @Test
    void testHospitalDetailsConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new HospitalDetailsConstants();
        });
    }

}
