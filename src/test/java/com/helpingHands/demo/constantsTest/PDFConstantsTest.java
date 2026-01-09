package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.PDFConstants;

public class PDFConstantsTest {

    @Test
    void testPDFConstantsValues() {
        assertNotNull(PDFConstants.PDF_GENERATION_ERROR);
        assertEquals("Error occurred while generating the PDF document.", PDFConstants.PDF_GENERATION_ERROR);
    }
    
    @Test
    void testPDFConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new PDFConstants();
        });
    }
}
