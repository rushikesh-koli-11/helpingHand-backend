package com.helpingHands.demo.mapperTest;

import com.helpingHands.demo.DTO.UpdatesDTO;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.Updates;
import com.helpingHands.demo.mapper.UpdatesMapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class UpdatesMapperTest {

	@Test
	void testToDTO() {
	    Fundraiser fundraiser = new Fundraiser();
	    fundraiser.setId(1);

	    Updates updates = new Updates();
	    updates.setUpdateId(1);
	    updates.setFundraiser(fundraiser);
	    updates.setContent("Test update content");
	    updates.setCreatedAt(LocalDateTime.of(2024, 3, 4, 10, 0));

	    UpdatesDTO updatesDTO = UpdatesMapper.toDTO(updates);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");
	    String expectedFormattedDate = updates.getCreatedAt().format(formatter);

	    assertNotNull(updatesDTO);
	    assertEquals(1, updatesDTO.getUpdateId());
	    assertEquals(1, updatesDTO.getFundraiserId());
	    assertEquals("Test update content", updatesDTO.getContent());
	    assertEquals(expectedFormattedDate, updatesDTO.getCreatedAt());
	}


    @Test
    void testToEntity() {
        UpdatesDTO updatesDTO = UpdatesDTO.builder()
                .content("New update content")
                .build();

        Updates updates = UpdatesMapper.toEntity(updatesDTO);

        assertNotNull(updates);
        assertEquals("New update content", updates.getContent());
    }
}