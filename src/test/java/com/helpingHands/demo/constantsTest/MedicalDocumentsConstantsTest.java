package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.MedicalDocumentsConstants;

public class MedicalDocumentsConstantsTest {

    @Test
    void testMedicalDocumentsConstantsValues() {
        assertNotNull(MedicalDocumentsConstants.MEDICAL_DOCUMENTS_NOT_FOUND);
        assertEquals("Medical documents not found for fundraiser ID: ", MedicalDocumentsConstants.MEDICAL_DOCUMENTS_NOT_FOUND);

        assertNotNull(MedicalDocumentsConstants.FILE_CONVERSION_ERROR);
        assertEquals("Error converting file to byte array", MedicalDocumentsConstants.FILE_CONVERSION_ERROR);

        assertNotNull(MedicalDocumentsConstants.FILE_UPDATE_ERROR);
        assertEquals("Error updating file", MedicalDocumentsConstants.FILE_UPDATE_ERROR);
    }
    
    @Test
    void testMedicalDocumentsConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new MedicalDocumentsConstants();
        });
    }

}

