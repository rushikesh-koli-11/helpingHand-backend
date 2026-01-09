package com.helpingHands.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.services.serviceImpl.OpenAIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openai")
public class OpenAIController {

    private final OpenAIService openAIService;

    // Generating a post using OpenAI based on the provided prompt
    @PostMapping("/generate-post")
    public String generatePost(@RequestBody String prompt) {
        return openAIService.generatePost(prompt);
    }
}