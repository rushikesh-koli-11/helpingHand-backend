package com.helpingHands.demo.services.serviceImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.helpingHands.demo.constants.OpenAIConstants;

import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the OpenAI API.
 * This class provides functionality for generating content using OpenAI's GPT-4 model.
 */
@Service
public class OpenAIService {

    // API endpoint for OpenAI
    private final String OPENAI_API_URL = OpenAIConstants.OPENAI_API_URL;

    // API key for authenticating requests to OpenAI
    private final String API_KEY = OpenAIConstants.API_KEY;

    /**
     * Generates a post using the OpenAI GPT-4 model based on the provided prompt.
     *
     * @param prompt The input prompt for generating content.
     * @return The generated content as a String. Returns null if an exception occurs.
     */
    public String generatePost(String prompt) {
        try {
            // Creating a RestTemplate instance for making HTTP requests
            RestTemplate restTemplate = new RestTemplate();

            // Setting up headers for the API request
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY.trim()); // Adding the API key for authentication
            headers.set("Content-Type", "application/json"); // Setting content type to JSON

            // Building the request body in JSON format
            String requestBody = "{\n" +
                    "  \"model\": \"gpt-4\",\n" + // Specifying the GPT-4 model
                    "  \"prompt\": \"" + prompt + "\",\n" + // Including the user-provided prompt
                    "  \"max_tokens\": 100\n" + // Limiting the response to 100 tokens
                    "}";

            // Creating an HttpEntity with the request body and headers
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Making a POST request to the OpenAI API
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_API_URL, // API endpoint
                    HttpMethod.POST, // HTTP method
                    entity, // Request entity
                    String.class // Expected response type
            );

            // Extracting the generated content from the response
            String postContent = response.getBody();
            return postContent;
        } catch (Exception e) {
            // Logging the exception and returning null in case of an error
            e.printStackTrace();
            return null;
        }
    }
}