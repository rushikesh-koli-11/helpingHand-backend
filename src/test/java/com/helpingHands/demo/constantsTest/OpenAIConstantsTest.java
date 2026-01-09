package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.OpenAIConstants;

public class OpenAIConstantsTest {

    @Test
    void testOpenAIConstantsValues() {
        assertNotNull(OpenAIConstants.OPENAI_API_URL);
        assertEquals("https://api.openai.com/v1/completions", OpenAIConstants.OPENAI_API_URL);

        assertNotNull(OpenAIConstants.API_KEY);
        assertFalse(OpenAIConstants.API_KEY.isEmpty());
    }
    
    @Test
    void testOpenAIConstantsClassCannotBeInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new OpenAIConstants();
        });
    }
}
