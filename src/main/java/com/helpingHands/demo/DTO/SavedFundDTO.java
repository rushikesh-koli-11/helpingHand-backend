package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedFundDTO {
	private String saveId;
	private String userId;
	private String fundraiserId;
}
