package com.helpingHands.demo.services.serviceImpl;

import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.helpingHands.demo.constants.PDFConstants;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.services.PDFService;

/**
 * Implementation of {@link PDFService}.
 * This service is responsible for generating PDF receipts from HTML content.
 */
@Service
public class PDFServiceImpl implements PDFService {

    /**
     * Generates a PDF receipt from the given HTML content.
     * 
     * @param htmlContent The HTML content to be converted into a PDF.
     * @return A byte array representing the generated PDF.
     * @throws Exception If an error occurs during PDF generation.
     */
    @Override
    public byte[] generateReceipt(String htmlContent) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // Use Flying Saucer's ITextRenderer to render the HTML as a PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent); // Render HTML content
            renderer.layout(); // Prepare layout
            renderer.createPDF(outputStream); // Create the PDF

            return outputStream.toByteArray(); // Return the PDF as byte array
        } catch (Exception e) {
            // Log the error for debugging and rethrow the exception with a custom message
            System.err.println("Error generating PDF: " + e.getMessage());
            throw new CustomExceptions(PDFConstants.PDF_GENERATION_ERROR);
        }
    }
}
