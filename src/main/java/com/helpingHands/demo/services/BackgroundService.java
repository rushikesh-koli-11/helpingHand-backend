package com.helpingHands.demo.services;

import com.helpingHands.demo.DTO.BackgroundDTO;

public interface BackgroundService {
    BackgroundDTO createBackground(BackgroundDTO backgroundDTO);
    BackgroundDTO getBackgroundById(int backgroundId);
    void deleteBackground(int backgroundId);
    BackgroundDTO updateBackground(int id, BackgroundDTO backgroundDTO);

}


