package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.PaymentConstants;

public class PaymentConstantsTest {

    @Test
    void testPaymentConstantsValues() {
        assertNotNull(PaymentConstants.STRIPE_API_KEY);
        assertNotNull(PaymentConstants.DEFAULT_CURRENCY);
        assertNotNull(PaymentConstants.SUCCESS_URL);
        assertNotNull(PaymentConstants.CANCEL_URL);
        assertNotNull(PaymentConstants.INVALID_DONATION_AMOUNT);
        assertNotNull(PaymentConstants.PAYMENT_SESSION_CREATION_ERROR);

        assertEquals("INR", PaymentConstants.DEFAULT_CURRENCY);
        assertEquals("http://localhost:3000/donations/success?donationId=", PaymentConstants.SUCCESS_URL);
        assertEquals("http://localhost:3000/donations/cancel?donationId=", PaymentConstants.CANCEL_URL);
        assertEquals("Invalid donation amount.", PaymentConstants.INVALID_DONATION_AMOUNT);
        assertEquals("Error creating Stripe payment session", PaymentConstants.PAYMENT_SESSION_CREATION_ERROR);
    }
    
    @Test
    void testPaymentConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new PaymentConstants();
        });
    }
}
