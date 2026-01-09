package com.helpingHands.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.BackgroundDTO;
import com.helpingHands.demo.services.BackgroundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backgrounds")
@CrossOrigin(origins = "http://localhost:3000")
public class BackgroundController {

    private final BackgroundService backgroundService;

    // Handling creating a new background
    @PostMapping
    public BackgroundDTO createBackground(@RequestBody BackgroundDTO backgroundDTO) {
        return backgroundService.createBackground(backgroundDTO);
    }

    // Handling retrieving a background by ID
    @GetMapping("/{backgroundId}")
    public BackgroundDTO getBackgroundById(@PathVariable int backgroundId) {
        return backgroundService.getBackgroundById(backgroundId);
    }

    // Handling deleting a background by ID
    @DeleteMapping("/{backgroundId}")
    public void deleteBackground(@PathVariable int backgroundId) {
        backgroundService.deleteBackground(backgroundId);
    }
    
    // Handling updating a background by ID
    @PutMapping("/{backgroundId}")
    public ResponseEntity<BackgroundDTO> updateBackground(
            @PathVariable int backgroundId, 
            @RequestBody BackgroundDTO backgroundDTO) {
        BackgroundDTO updatedBackground = backgroundService.updateBackground(backgroundId, backgroundDTO);
        return ResponseEntity.ok(updatedBackground);
    }
}
