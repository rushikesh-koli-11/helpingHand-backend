package com.helpingHands.demo.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.helpingHands.demo.controller.OpenAIController;
import com.helpingHands.demo.services.serviceImpl.OpenAIService;

public class OpenAIControllerTest {

    @Mock
    private OpenAIService openAIService;

    @InjectMocks
    private OpenAIController openAIController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePost() {
        String prompt = "Write a motivational quote";
        String generatedPost = "Believe in yourself and all that you are.";

        when(openAIService.generatePost(prompt)).thenReturn(generatedPost);

        String response = openAIController.generatePost(prompt);
        assertEquals(generatedPost, response);
    }
}
