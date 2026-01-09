package com.helpingHands.demo.services.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret.key}")
    private String secretKey;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    
    public boolean verifyRecaptcha(String token) {
    	// If token is null or empty, verification fails
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
    	
        RestTemplate restTemplate = new RestTemplate();
        
        // Creating the body of the request with secret key and token
        Map<String, String> body = Map.of("secret", secretKey, "response", token);

        // Send request to Google reCAPTCHA API
        try {
            Map<?, ?> response = restTemplate.postForObject(VERIFY_URL, body, Map.class);
            System.out.println("reCAPTCHA verification response: " + response);
            
            if (response == null) {
                return false;
            }


            // Check if the "success" field in the response is true
            return Boolean.TRUE.equals(response.get("success"));
        } catch (Exception e) {
            return false;
        }
    }
}
