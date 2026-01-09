package com.helpingHands.demo.services;

import com.helpingHands.demo.DTO.BackgroundDTO;

public interface BackgroundService {
    BackgroundDTO createBackground(BackgroundDTO backgroundDTO);
    BackgroundDTO getBackgroundById(String backgroundId);
    BackgroundDTO getBackgroundByFundraiserId(String fundraiserId);
    void deleteBackground(String backgroundId);
    BackgroundDTO updateBackground(String id, BackgroundDTO backgroundDTO);
}


