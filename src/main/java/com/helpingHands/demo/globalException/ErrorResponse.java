package com.helpingHands.demo.globalException;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
	private LocalDateTime timestamp;
	private StackTraceElement[] message;
	private String details;
}
