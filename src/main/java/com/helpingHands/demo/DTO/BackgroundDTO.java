package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundDTO {
	private String backgroundId;
    private String fundraiserId;
    private String relationWithPatient;
    private Double monthlyIncomeOfPatientsFamily;
}
