package com.helpingHands.demo.mapper;

import com.helpingHands.demo.DTO.UpdatesDTO;
import com.helpingHands.demo.entities.Updates;

public class UpdatesMapper {

    public static UpdatesDTO toDTO(Updates updates) {
        return UpdatesDTO.builder()
                .updateId(updates.getUpdateId())
                .fundraiserId(updates.getFundraiser().getId())
                .content(updates.getContent())
                .createdAt(UpdatesDTO.formatDateTime(updates.getCreatedAt()))
                .build();
    }

    public static Updates toEntity(UpdatesDTO updatesDTO) {
        Updates updates = new Updates();
        updates.setContent(updatesDTO.getContent());
        return updates;
    }
}
