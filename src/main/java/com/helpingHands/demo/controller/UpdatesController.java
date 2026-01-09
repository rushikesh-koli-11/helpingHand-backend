package com.helpingHands.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpingHands.demo.DTO.UpdatesDTO;
import com.helpingHands.demo.services.UpdatesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/updates")
@CrossOrigin(origins = "http://localhost:3000")
public class UpdatesController {

	private final UpdatesService updatesService;


    @PostMapping
    public ResponseEntity<UpdatesDTO> postUpdate(@RequestBody UpdatesDTO updatesDTO) {
        UpdatesDTO createdUpdate = updatesService.postUpdate(updatesDTO);
        return ResponseEntity.ok(createdUpdate);
    }

    @GetMapping
    public ResponseEntity<List<UpdatesDTO>> getAllUpdates() {
        List<UpdatesDTO> updates = updatesService.getAllUpdates();
        return ResponseEntity.ok(updates);
    }

    @GetMapping("/{updateId}")
    public ResponseEntity<UpdatesDTO> getUpdateById(@PathVariable int updateId) {
        UpdatesDTO update = updatesService.getUpdateById(updateId);
        return ResponseEntity.ok(update);
    }
    
    @GetMapping("/fundraiser/{fundraiserId}")
    public ResponseEntity<List<UpdatesDTO>> getUpdatesByFundraiserId(@PathVariable int fundraiserId) {
        return ResponseEntity.ok(updatesService.getUpdatesByFundraiserId(fundraiserId));
    }
	
	
}
