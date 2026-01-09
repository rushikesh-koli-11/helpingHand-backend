package com.helpingHands.demo.constants;

public class PaymentConstants {
	public PaymentConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

	    // Stripe API Key
	    public static final String STRIPE_API_KEY = "api-key";

	    // Default Currency
	    public static final String DEFAULT_CURRENCY = "INR";

	    // Success and Failure URLs
	    public static final String SUCCESS_URL = "http://localhost:3000/donations/success?donationId=";
	    public static final String CANCEL_URL = "http://localhost:3000/donations/cancel?donationId=";

	    // Error Messages
	    public static final String INVALID_DONATION_AMOUNT = "Invalid donation amount.";
	    public static final String PAYMENT_SESSION_CREATION_ERROR = "Error creating Stripe payment session";
	    

}
