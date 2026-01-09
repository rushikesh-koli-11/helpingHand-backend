package com.helpingHands.demo.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatesDTO {
	
	private int updateId;
    private int fundraiserId;
    private String content;
    private String createdAt;

    // Formatting LocalDateTime to "10:30, 20 Dec 2025"
    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");
        return dateTime.format(formatter);
    }
}
