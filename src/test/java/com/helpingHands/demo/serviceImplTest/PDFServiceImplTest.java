package com.helpingHands.demo.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.helpingHands.demo.constants.PDFConstants;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.services.serviceImpl.PDFServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PDFServiceImplTest {

    @InjectMocks
    private PDFServiceImpl pdfService;

    private String validHtmlContent;
    private String invalidHtmlContent;

    @BeforeEach
    void setUp() {
        validHtmlContent = "<html><body><h1>Test PDF</h1></body></html>";
        invalidHtmlContent = "<html><body><h1>Unclosed Tag"; // Invalid HTML
    }

    @Test
    void testGenerateReceipt_Success() throws Exception {
        byte[] pdfBytes = pdfService.generateReceipt(validHtmlContent);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0, "Generated PDF should not be empty");
    }

    @Test
    void testGenerateReceipt_InvalidHtml_ThrowsException() {
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            pdfService.generateReceipt(invalidHtmlContent);
        });

        assertEquals(PDFConstants.PDF_GENERATION_ERROR, exception.getMessage()); 
    }

}